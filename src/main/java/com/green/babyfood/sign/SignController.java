package com.green.babyfood.sign;

import com.green.babyfood.sign.model.SignInResultDto;
import com.green.babyfood.sign.model.SignUpResultDto;
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
public class SignController {

    private final SignService SERVICE;

    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.

    @PostMapping("/sign-in")
    public SignInResultDto signIn(HttpServletRequest req, @RequestParam String email, @RequestParam String password) throws RuntimeException {

        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. email: {}, pw: {}, ip: {}", email, password, ip);

        return SERVICE.signIn(email, password, ip);
    }

    @PostMapping("/sign-up")
    public SignUpResultDto signUp(@RequestParam String email
                                , @RequestParam String password
                                , @RequestParam String nm
                                , @RequestParam String mobileNb
                                , @RequestParam String role
                                , @RequestParam String address
                                , @RequestParam String addressDetail
                                , @RequestParam String nickNm) {
        log.info("[signUp] 회원가입을 수행합니다. email: {}, pw: {}, nm: {}, mobileNb: {}, role: {}" +
                ", address : {}, addressDetail : {}, nickNm : {}", email, password, nm, mobileNb,role, address, addressDetail, nickNm);
        SignUpResultDto dto = SERVICE.signUp(email, password, nm, mobileNb, role, address, addressDetail, nickNm);
        log.info("[signUp] 회원가입 완료 email: {}", email);
        return dto;
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<SignUpResultDto> refreshToken(HttpServletRequest req
            , @RequestParam String refreshToken) {
        SignUpResultDto dto = SERVICE.refreshToken(req, refreshToken);
        return dto == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) : ResponseEntity.ok(dto);
    }
}
