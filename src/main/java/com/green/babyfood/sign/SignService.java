package com.green.babyfood.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.CommonRes;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.UserDetailsMapper;
import com.green.babyfood.config.security.model.RedisJwtVo;
import com.green.babyfood.config.security.model.UserEntity;
import com.green.babyfood.config.security.model.UserTokenEntity;
import com.green.babyfood.config.security.otp.TOTP;
import com.green.babyfood.sign.model.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.green.babyfood.util.EmailValidator.emailValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserDetailsMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final RedisService REDIS_SERVICE;
    private final AuthenticationFacade FACADE;
    private final ObjectMapper OBJECT_MAPPER;

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

    public SignInResultDto signIn(SigninDto dto1, String ip) throws Exception {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        UserEntity user = MAPPER.getByUid(dto1.getEmail());

        log.info("[getSignInResult] email: {}", dto1.getEmail());

        log.info("[getSignInResult] 패스워드 비교");
        if(!PW_ENCODER.matches(dto1.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 다름");
        }
        log.info("[getSignInResult] 패스워드 일치");

        // RT가 이미 있을 경우
        String redisKey = String.format("RT(%s):%s:%s", "Server", user.getIuser(), ip);
        if(REDIS_SERVICE.getValues(redisKey) != null) {
            REDIS_SERVICE.deleteValues(redisKey); // 삭제
        }

        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        // Redis에 RT 저장
        RedisJwtVo redisJwtVo = RedisJwtVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(redisJwtVo); //오류가 나서 빵야 함
        REDIS_SERVICE.setValues(redisKey, value);

        /*
        UserTokenEntity tokenEntity = UserTokenEntity.builder()
                .iuser(user.getIuser())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .ip(ip)
                .build();

        int result = MAPPER.updUserToken(tokenEntity);

        log.info("[getSignInResult] SignInResultDto 객체 생성");

         */
        SignInResultDto dto = SignInResultDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        setSuccessResult(dto);
        return dto;
    }

    public void logout(HttpServletRequest req) {
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Long iuser = FACADE.getLoginUserPk();
        String ip = req.getRemoteAddr();

        // Redis에 저장되어 있는 RT 삭제
        String redisKey = String.format("RT(%s):%s:%s", "Server", iuser, ip);
        String refreshTokenInRedis = REDIS_SERVICE.getValues(redisKey);
        if (refreshTokenInRedis != null) {
            REDIS_SERVICE.deleteValues(redisKey);
        }
        // Redis에 로그아웃 처리한 AT 저장
        //long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY) - new Date().getTime();
        long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY)
                - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("date-getTime(): {}", new Date().getTime());
        log.info("localDateTime-getTime(): {}", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        REDIS_SERVICE.setValuesWithTimeout(accessToken, "logout", expiration);  //남은시간 이후가 되면 삭제가 되도록 함.

    }

    public SignInResultDto refreshToken(HttpServletRequest req, String refreshToken) {
        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) {
            return null;
        }

        String ip = req.getRemoteAddr();
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Claims claims = null;
        try {
            claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (claims == null) {
            return null;
        }

        String strIuser = claims.getSubject();
        Long iuser = Long.valueOf(strIuser);

        String redisKey = String.format("RT(%s):%s:%s", "Server", iuser, ip);
        String value = REDIS_SERVICE.getValues(redisKey);
        if (value == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }
        try {
            RedisJwtVo redisJwtVo = OBJECT_MAPPER.readValue(value, RedisJwtVo.class);
            if(!redisJwtVo.getAccessToken().equals(accessToken)
                    || !redisJwtVo.getRefreshToken().equals(refreshToken)) {
                return null;
            }

            List<String> roles = (List<String>)claims.get("roles");
            String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);

            //redis 업데이트
            RedisJwtVo updateRedisJwtVo = RedisJwtVo.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(redisJwtVo.getRefreshToken())
                    .build();
            String upateValue = OBJECT_MAPPER.writeValueAsString(updateRedisJwtVo);
            REDIS_SERVICE.setValues(redisKey, upateValue);

            return SignInResultDto.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        /*
        UserTokenEntity p = UserTokenEntity.builder()
                .iuser(iuser)
                .ip(ip)
                .build();
        UserTokenEntity selResult = MAPPER.selUserToken(p);
        if(selResult == null || !(selResult.getAccessToken().equals(accessToken) && selResult.getRefreshToken().equals(refreshToken))) {
            return null;
        }
        */


        /*
        UserTokenEntity tokenEntity = UserTokenEntity.builder()
                .iuser(iuser)
                .ip(ip)
                .accessToken(reAccessToken)
                .refreshToken(refreshToken)
                .build();

        int updResult = MAPPER.updUserToken(tokenEntity);
*/

    }

    public int updSecretKey(Long iuser, String secretKey) {
        UserUpdDto dto = new UserUpdDto();
        dto.setIuser(iuser);
        dto.setSecretKey(secretKey);

        return MAPPER.updSecretKey(dto);
    }

    public boolean otpValid(String inputCode) {
        UserEntity entity = MAPPER.getByUid("abcdf@naver.com");
        String code = getTOTPCode(entity.getSecretKey());
        return code.equals(inputCode);
    }

    // OTP 검증 요청 때마다 개인키로 OTP 생성
    private String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        // 실제로는 로그인한 회원에게 생성된 개인키가 필요합니다.
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
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

