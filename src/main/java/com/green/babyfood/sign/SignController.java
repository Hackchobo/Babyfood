package com.green.babyfood.sign;

import com.green.babyfood.CommonRes;
import com.green.babyfood.config.security.model.UserEntity;
import com.green.babyfood.config.security.otp.OtpRes;
import com.green.babyfood.config.security.otp.TOTP;
import com.green.babyfood.config.security.otp.TOTPTokenGenerator;
import com.green.babyfood.sign.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
@Tag(name = "유저/관리자 회원 가입")
public class SignController {

    private final SignService SERVICE;
    private final TOTPTokenGenerator totp;
    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.

    @PostMapping("/sign-in")
    @Operation(summary = "로그인",description =
            "email : 회원의 이메일(아이디가)<br>"+
                    "password: 회원의 비밀번호<br>" +
                    "참고사항 : password 가 틀릴경우 경고창이 나와요<br>"+
                    "참고사항 : 로그인시 액세스 토큰과 리프레시 토큰이 발급되요"
    )
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody SigninDto dto) throws Exception {

        String ip = req.getRemoteAddr();
//        log.info("[signIn] 로그인을 시도하고 있습니다. email: {}, pw: {}, ip: {}", email, password, ip);

        return SERVICE.signIn(dto, ip);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입",description =
            "email : 회원의 이메일(아이디가)<br>"+
                    "password: 회원의 비밀번호<br>" +
                    "nm : 회원의 이름<br>" +
                    "mobileNb : 전화번호<br>" +
                    "role : 관리자 유무 <br>" +
                    "zipCode : 우편번호 <br>" +
                    "address : 주소<br>" +
                    "addressDetail : 상세주소<br>" +
                    "nickNm : 닉네임" +
                    "참고사항 : email 과 nickNm이 곁칠경우 경고창이 나와요"
    )
    public SignUpResultDto signUp(@RequestBody SignEntity entity) {
        /*log.info("[signUp] 회원가입을 수행합니다. email: {}, pw: {}, nm: {}, mobileNb: {}, role: {}" +
                ", address : {}, addressDetail : {}, nickNm : {}", email, password, nm, mobileNb,role, address, addressDetail, nickNm);*/
        SignUpResultDto dto = SERVICE.signUp(entity);
//        log.info("[signUp] 회원가입 완료 email: {}", email);
        return dto;
    }

    @GetMapping("/refresh-token")
    @Operation(summary = "토큰발행",description =
            "refreshToken : 리프레시 토큰을 입력하면 됩니다.<br>"+
                    "참고사항 : 액세스토큰이 재발행 됩니다. <br>" +
                    "참고사항 : 로그인이 되어있는 상태에서만 발행이 됩니다."
    )
    public ResponseEntity<SignUpResultDto> refreshToken(HttpServletRequest req
            , @RequestParam String refreshToken) {
        SignUpResultDto dto = SERVICE.refreshToken(req, refreshToken);
        return dto == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) : ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃",description =
               "참고사항 : 로그인이 되어있는 상태에서 로그아웃을 하면 됩니다.(액티브토큰이 삭제됨)"
    )
    public ResponseEntity<?> logout(HttpServletRequest req) {
        SERVICE.logout(req);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "") // 프론트엔드에 쿠키에 저장하는값을 name의 이름으로 한다면
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/otp")
    @Operation(summary = "otp(TEST), QR코드 발급",description =
            "참고사항 : 테스트중입니다. 사용법은 따로 알려드리겠습니다."
    )
    public ResponseEntity<?> otp() {
        // secretKey 생성
        String secretKey = totp.generateSecretKey();
        System.out.println(secretKey);
        String account = "pirbak@google.com";
        String issuer = "otpTest";
        // secretKey + account + issuer => QR 바코드 생성
        String barcodeUrl = totp.getGoogleAuthenticatorBarcode(secretKey, account, issuer);
        OtpRes res = OtpRes.builder().secretKey(secretKey).barcodeUrl(barcodeUrl).build();
        SERVICE.updSecretKey(1L, secretKey);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/otp-valid")
    @Operation(summary = "otp(TEST)",description =
            "참고사항 : 테스트중입니다. 사용법은 따로 알려드리겠습니다."
    )
    public ResponseEntity<?> otpValid(@RequestParam String inputCode) {
        return ResponseEntity.status(HttpStatus.OK).body(SERVICE.otpValid(inputCode));
    }


    // -----------------비밀번호 찾기 테스트용
    // 임시로 진행하는 코드이므로 다른 내용과 호환되지 않을 수 있습니다
    // 관련해서 오류가 날 경우 이 아래 전부 주석처리해주면 됩니다.

    @GetMapping("/password/find")
    @Operation(summary = "비밀번호 찾기 페이지",description =
            "테스트용 : 아이디(이메일)과 휴대폰 번호를 입력해주세요.<br>" +
                    "비교하여 회원정보와 일치 시 등록된 메일 주소로 임시 비밀번호를 발송합니다"
    )
    public String findPassword(@RequestParam String mail, @RequestParam String mobileNb){
        log.info("테스트");
        SERVICE.findPassword(mail, mobileNb);
        return SERVICE.findPassword(mail, mobileNb);
    }


    @PostMapping("/email")
    @Operation(summary = "이메일 중복체크")
    public int emailCheck(String email){
        return SERVICE.emailCheck(email);
    }
    @GetMapping("/nickname")
    @Operation(summary = "닉네임 중복체크" ,
            description = "return : 1이면 중복인것")
    int getNickNamecheck(@RequestParam String nickname){
        return SERVICE.nicknmcheck(nickname);
    }

}
