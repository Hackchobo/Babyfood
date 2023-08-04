package com.green.babyfood.sign;

import com.green.babyfood.sign.model.SignEntity;
import com.green.babyfood.sign.model.SignInResultDto;
import com.green.babyfood.sign.model.SignUpResultDto;
import com.green.babyfood.sign.model.SigninDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
@Tag(name = "유저/관리자 회원 가입")
public class SignController {

    private final SignService SERVICE;

    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.

    @PostMapping("/sign-in")
    @Operation(summary = "로그인",description =
            "email : 회원의 이메일(아이디가)<br>"+
                    "password: 회원의 비밀번호<br>" +
                    "참고사항 : password 가 틀릴경우 경고창이 나와요<br>"+
                    "참고사항 : 로그인시 액세스 토큰과 리프레시 토큰이 발급되요"
    )
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody SigninDto dto) throws RuntimeException {

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
}
