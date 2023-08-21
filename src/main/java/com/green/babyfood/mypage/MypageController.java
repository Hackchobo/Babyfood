package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import com.green.babyfood.user.model.CreatePicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Tag(name = "프로필(내정보) , 주문내역")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService service;

    @GetMapping("/orderlist")
    @Operation(summary = "주문내역조회",description = ""+
            "iuser: 유저PK <br>"+
            "month: 조회하고싶은 기간(개월) <br>")
    List<OrderlistSelDto> getOrderlistMonths(@RequestParam  int month){
        return service.Orderlist(month);
    }

    @GetMapping("/orderlist/{orderId}")
    @Operation(summary = "상세주문내역",description = "")
    OrderlistSelUserDto getOrderlistDetail(@PathVariable Long orderId){
        return service.OrderlistDetail(orderId);
    }

    @DeleteMapping("/orderlist")
    @Operation(summary = "주문내역삭제",description = ""+
            "orderId: 주문내역PK <br>")
    public int delorderlist(Long orderId){
        return service.delorder(orderId);
    }

    @GetMapping("/profile")
    @Operation(summary = "내 정보조회",description = ""+
            "image: 프로필이미지<br>"+
            "address: 주소 <br>")
    ProfileSelDto getprofile(){
        return service.profile();
    }

    @PatchMapping("/profile")
    @Operation(summary = "내정보(유저 정보) 수정" , description = "")
    int patchprofile(@RequestBody ProfileUpdDto dto){
        return service.updProfile(dto);
    }
    @GetMapping("/profile/nickname")
    @Operation(summary = "닉네임 중복체크" ,
            description = "return : 1이면 중복인것")
    int getNickNamecheck(@RequestParam String nickname){
        return service.nicknmcheck(nickname);
    }

    @DeleteMapping("/profile")
    @Operation(summary = "회원탈퇴")
    int delprofile(){
        return service.delUser();
    }

    @PatchMapping(value = "/profile/pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 사진수정",description =
            "iuser : 회원의 고유값(PK) <- 해당 유저가 수정됨<br>"+
                    "pic : 사진 넣는 부분")
    public int patchPic(@RequestParam MultipartFile pic){
        return service.updPicUser(pic);
    }

}
