package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "유저/관리자 회원관리")
public class UserController {

    private final UserService service;

    @GetMapping("/seach")
    @Operation(summary = "유저/관리자 회원정보보기",description =
            "나타나는 정보 : 회원의 고유값(PK), 이메일, 비밀번호, 이미지, 이름, 생년월일, 모바일 번호, 생성일, 관리자 여부" +
                    "우편번호, 주소, 상세주소, 닉네임, 포인트<br>"
    )
    public List<UserEntity1> getUser(){
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
            "zipCode: 우편번호<br>" +
            "address: 회원의 주소<br>" +
            "addressDetaile : 상세주소"
    )
    public int patchUser(@RequestBody UserUpdDto1 dto){
        return service.updUser(dto);
    }

    @PatchMapping(value = "/pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저/관리자 사진등록",description =
            "iuser : 회원의 고유값(PK) <- 해당 유저가 수정됨<br>"+
            "pic : 사진 넣는 부분"
    )
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
