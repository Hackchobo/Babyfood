package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "유저/관리자 회원관리")
public class UserController {

    private final UserService service;

    @PostMapping("/create/user")
    @Operation(summary = "유저회원가입",description = "email : 회원의 이메일(아이디가 될정보)<br>"+
            "nickNm:회원의 닉네임<br>" +
            "password: 회원의 비밀번호<br>" +
            "secret: 비밀번호 확인<br>" +
            "name: 회원의 이름<br>" +
            "mobileNb: 회원의 전화번호<br>" +
            "birthday: 아기의 생년월일<br>" +
            "address: 회원의 주소<br>" +
            "참고사항 : password 가 secret과 다를경우 -1을 리턴합니다."
    )
    public int postUser(@RequestBody UserInsDto dto){
        // dto.setSecret(password);
        return service.insUser(dto);
    }

    @PostMapping("/create/admin")
    @Operation(summary = "관리자 회원가입",description = "email : 관리자의 이메일(아이디가 될정보)<br>"+
            "nickNm:관리자의 닉네임<br>" +
            "password: 관리자의 비밀번호<br>" +
            "name: 관리자의 이름<br>" +
            "mobileNb: 관리자의 전화번호<br>" +
            "birthday: 관리자의 생년월일<br>" +
            "address: 관리자의 주소"
    )
    public int postAdmin(@RequestBody AdminInsDto dto){
        return service.insAdmin(dto);
    }

    @GetMapping("/seach")
    @Operation(summary = "유저/관리자 회원정보보기",description =
            "나타나는 정보 : 회원의 고유값(PK), 이메일, 비밀번호, 이미지, 이름, 생년월일, 모바일 번호, 생성일, 관리자 여부" +
                    ", 주소, 닉네임, 포인트<br>"
    )
    public List<UserEntity> getUser(){
        return service.selUser();
    }

    @PutMapping("/pix")
    @Operation(summary = "유저/관리자 회원수정",description = "iuser : 회원의 고유값(PK) <- 해당 유저가 수정됨<br>"+
            "email : 회원의 이메일(아이디가 될정보)<br>" +
            "nickNm:회원의 닉네임<br>" +
            "password: 회원의 비밀번호<br>" +
            "name: 회원의 이름<br>" +
            "mobileNb: 회원의 전화번호<br>" +
            "birthday: 아기의 생년월일<br>" +
            "address: 회원의 주소"
    )
    public int patchUser(@RequestBody UserUpdDto dto){
        return service.updUser(dto);
    }

    @PatchMapping(value = "/pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int patchPic(@RequestPart MultipartFile pic, @RequestParam Long iuser){
        CreatePicDto dto = new CreatePicDto();
        dto.setIuser(iuser);
        return service.updPicUser(pic, dto);
    }

    @PatchMapping("/point/{iuser}")
    @Operation(summary = "유저/관리자 포인트",description = "iuser : 회원의 고유값(PK) <- 해당 유저가 수정됨<br>"+
            "point : 유저의 포인트"
    )
    public int patchPoint(@PathVariable Long iuser, @RequestParam int point){
        UserPointDto dto = new UserPointDto();
        dto.setIuser(iuser);
        dto.setPoint(point);
        return service.updPointUser(dto);
    }

    @DeleteMapping("/{iuser}")
    @Operation(summary = "유저/관리자 삭제",description = "iuser : 회원의 고유값(PK) <- 해당 유저가 삭제됨<br>")
    public int delUser(@PathVariable Long iuser){
        UserDelDto dto = new UserDelDto();
        dto.setIuser(iuser);
        return service.delUser(dto);
    }

}
