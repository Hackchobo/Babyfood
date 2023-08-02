package com.green.babyfood.admin;

import com.green.babyfood.admin.model.AdminProductInsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자페이지")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;


    @PostMapping("/ins")
    @Operation(summary = "상품 등록", description = ""+
            "title = 타이틀 <br>" +
            "name = 이름 <br>" +
            "price = 가격<br>" +
            "quantity = 재고<br>" +
            "description = 제품설명 <br>" +
            "allergy = 알러지정보 <br>")
    int productIns(AdminProductInsDto dto){
        return service.productIns(dto);
    }
}
