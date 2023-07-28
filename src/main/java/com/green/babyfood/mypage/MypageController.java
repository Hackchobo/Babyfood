package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "마이페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService service;

    @GetMapping()
    @Operation(summary = "마이페이지에서 주문내역조회",description = ""+
            "name: 상품이름"+
            "shipment: 상품배송상태 <br>"+
            "nickNm : 닉네임<br>"+
            "point : 포인트<br>")
    List<SelMypageOrderListDto> getOrderList(int iuser){
        return service.mypageOrderlist(iuser);
    }

//    @GetMapping("/orderlist")
//    @Operation(summary = "나의 주문내역조회",description = ""+
//            "name: 상품이름"+
//            "shipment: 상품배송상태 <br>"+
//            "nickNm : 닉네임<br>"+
//            "point : 포인트<br>")
//    List<SelOrderlistDto>getOrderlist(int iuser){
//        return service.Orderlist(iuser);
//    }

    @GetMapping("/orderlist/months")
    @Operation(summary = "주문내역조회",description = ""+
            "iuser: 유저PK <br>"+
            "num: 조회하고싶은 기간(개월) <br>")
    List<SelOrderlistDto>getOrderlistMonths(SelOrderlistMonthsDto dto){
        return service.selOneMonths(dto);
    }
    @GetMapping("/orderlist/detail")
    @Operation(summary = "상세주문내역",description = "")
    List<SelOrderlistDetailDto>getOrderlistDetail(SelOrderlistDetailVo vo){
        return service.OrderlistDetail(vo);
    }


    @GetMapping("/profile")
    @Operation(summary = "내 정보조회",description = ""+
            "image: 프로필이미지<br>"+
            "address: 주소 <br>")
    SelprofileDto getprofile(int iuser){
        return service.profile(iuser);
    }
    @PatchMapping("/profile")
    @Operation(summary = "내정보 수정" , description = ""+
            "return 0: 비밀번호 일치X<br>"+
            "return -1 : 이미 있는 닉네임 <br>"+
            "return 1 : 수정완료")
    int patchprofile(@RequestBody UpdProfileDto dto){
        return service.UpdProfileDto(dto);
    }

    @DeleteMapping("/profile")
    @Operation(summary = "회원탈퇴")
    int delprofile(int iuser){
        //ddd
        return service.delUser(iuser);
    }


}
