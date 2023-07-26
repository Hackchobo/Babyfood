package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.SelOrderlistDto;
import com.green.babyfood.mypage.model.SelprofileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            "shipment: 상품배송상태 "+
            "nickNm : 닉네임"+
            "point : 포인트")
    List<SelOrderlistDto> getOrderlist(int iuser){
        return service.mypageOrderlist(iuser);
    }

    @GetMapping("/profile")
    @Operation(summary = "프로필조회",description = ""+
            "image: 프로필이미지"+
            "address: 주소 "+
            "nickNm : 닉네임"+
            "point : 포인트")
    SelprofileDto profile(int iuser){
        return service.profile(iuser);
    }


}
