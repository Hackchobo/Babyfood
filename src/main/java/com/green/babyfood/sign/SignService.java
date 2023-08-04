package com.green.babyfood.sign;

import com.green.babyfood.CommonRes;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.UserDetailsMapper;
import com.green.babyfood.config.security.model.UserEntity;
import com.green.babyfood.config.security.model.UserTokenEntity;
import com.green.babyfood.sign.model.SignEntity;
import com.green.babyfood.sign.model.SignInResultDto;
import com.green.babyfood.sign.model.SignUpResultDto;
import com.green.babyfood.sign.model.SigninDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserDetailsMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;

    public SignUpResultDto signUp(SignEntity entity) {
        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");
        entity.setPassword(PW_ENCODER.encode(entity.getPassword()));
        int result = MAPPER.save(entity);
        SignUpResultDto dto = new SignUpResultDto();

        if(result == 1) {
            log.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(dto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(dto);
        }
        return dto;
    }

    public SignInResultDto signIn(SigninDto dto1, String ip) throws RuntimeException {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        UserEntity user = MAPPER.getByUid(dto1.getEmail());

        log.info("[getSignInResult] email: {}", dto1.getEmail());

        log.info("[getSignInResult] 패스워드 비교");
        if(!PW_ENCODER.matches(dto1.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 다름");
        }
        log.info("[getSignInResult] 패스워드 일치");


        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);
        UserTokenEntity tokenEntity = UserTokenEntity.builder()
                .iuser(user.getIuser())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .ip(ip)
                .build();

        int result = MAPPER.updUserToken(tokenEntity);

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto dto = SignInResultDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        setSuccessResult(dto);
        return dto;
    }

    public SignInResultDto refreshToken(HttpServletRequest req, String refreshToken) throws RuntimeException {
        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) {
            return null;
        }

        String ip = req.getRemoteAddr();
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Claims claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
        if(claims == null) {
            return null;
        }
        String strIuser = claims.getSubject();
        Long iuser = Long.valueOf(strIuser);
        List<String> roles = (List<String>)claims.get("roles");

        UserTokenEntity p = UserTokenEntity.builder()
                .iuser(iuser)
                .ip(ip)
                .build();
        UserTokenEntity selResult = MAPPER.selUserToken(p);
        if(selResult == null || !(selResult.getAccessToken().equals(accessToken) && selResult.getRefreshToken().equals(refreshToken))) {
            return null;
        }

        String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        UserTokenEntity tokenEntity = UserTokenEntity.builder()
                .iuser(iuser)
                .ip(ip)
                .accessToken(reAccessToken)
                .refreshToken(refreshToken)
                .build();

        int updResult = MAPPER.updUserToken(tokenEntity);

        return SignInResultDto.builder()
                .accessToken(reAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonRes.SUCCESS.getCode());
        result.setMsg(CommonRes.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonRes.FAIL.getCode());
        result.setMsg(CommonRes.FAIL.getMsg());
    }
}

