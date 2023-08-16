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
import com.green.babyfood.email.EmailController;
import com.green.babyfood.email.model.MailSendDto;
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
import java.util.Random;

import static com.green.babyfood.util.EmailValidator.emailValidator;
import static com.green.babyfood.email.EmailController.*;

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
    private final SignMapper SIGN_MAPPER; // 비번찾기용

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
        REDIS_SERVICE.setValues(redisKey, refreshToken);

        /*
        UserTokenEntity tokenEntity = UserTokenEntity.builder()
                .iuser(user.getIuser())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .ip(ip)
                .build();

        int result = MAPPER.updUserToken(tokenEntity);


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
        String redisKey = String.format("a:RT(%s):%s:%s", "Server", iuser, ip);
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
        String ip = req.getRemoteAddr();

        String redisKey = String.format("RT(%s):%s:%s", "Server", iuser, ip);
        String redisRt = REDIS_SERVICE.getValues(redisKey);
        if (redisRt == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }
        try {
            if(!redisRt.equals(refreshToken)) {
                return null;
            }

            List<String> roles = (List<String>)claims.get("roles");
            String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);

            SignInResultDto builder=SignInResultDto.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(refreshToken)
                    .build();
            setSuccessResult(builder);
            return builder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

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

    public String findPassword(String mail, String mobileNb) {

        // email을 기준으로 DB의 유저 정보와 비교
        SignPwDto inputDto = new SignPwDto();
        inputDto.setMail(mail);
        inputDto.setMobileNb(mobileNb);

        SignPwDto dataDto = SIGN_MAPPER.findPassword(mail, mobileNb);

        if (inputDto.equals(dataDto)){
            log.info("회원정보 일치, 비밀번호 변경 시작");
            MailSendDto dto = new MailSendDto();
            String pw = updPassword(); // 임시 비밀번호 생성
            SIGN_MAPPER.updPassword(dataDto.getIuser(), pw); // DB의 비밀번호 변경
            dto.setTitle("비밀번호 변경 메일입니다");
            dto.setCtnt("임시 비밀번호 : " + pw + "\n 임시 비밀번호를 이용하여 로그인 후, 사용하고자 하는 비밀번호로 변경하세요.");
            dto.setMailAddress(mail);
            EmailController.postSend(dto);
            return "회원정보 일치 / 임시 비밀번호 메일 발송";
        }
        else {
            return "회원정보 불일치 / 확인 후 다시 시도하세요";
        }
    }

    public String updPassword() {
        // 임시 비밀번호 생성 -> 0~9, 알파벳 대소문자
        int index = 0;
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer password = new StringBuffer();
        Random random = new Random();

        final int PASSWORDLENGTH = 10; // 임시비밀번호 길이

        for (int i = 0; i < PASSWORDLENGTH ; i++) {
            double rd = random.nextDouble();
            index = (int) (charSet.length * rd);
            password.append(charSet[index]);
        }
        return password.toString();
    }

    public int emailCheck(String email){
        String result=SIGN_MAPPER.emailCheck(email);

        if(email.equals(result)){
            return 1;
        }
        return 0;
    }

    public int nicknmcheck(String nickNm){
        String result = SIGN_MAPPER.SelNickNm(nickNm);
        if (nickNm.equals(result)){
            return 1;
        }
        return 0;

    }

    public String findUserId(String mobileNb, String birthday) {
        // 입력 휴대폰 기준으로 DB의 유저 정보와 비교 진행
        SignIdDto inputDto = new SignIdDto();
        inputDto.setMobileNb(mobileNb);
        inputDto.setBirthday(birthday);

        SignIdDto dto = SIGN_MAPPER.findUserId(mobileNb); // DB에서 유저 생일을 가져온다.
        String result;
        if(birthday.equals(null)){
            log.info("null값 확인");
            return "정확한 정보를 입력해주세요";
        }
        else if (birthday.equals(dto.getBirthday())){
            // 회원이 입력한 생일과 db 저장된 생일이 일치하는 경우
            if (dto.getEamil().length() >= 4) {
                result = "##" + dto.getEamil().substring(2, dto.getEamil().length() - 2) + "##";
                // 이메일의 앞 2글자, 뒤 2글자를 ## 으로 처리한다
                // 4글자 이하라면 그대로 출력한다
            }
            else result = dto.getEamil();

            return "유저 아이디는 : " + result + "입니다";
        }
        else {
            return "회원정보가 일치하지 않습니다. 다시 확인해주세요";
        }

    }
}

