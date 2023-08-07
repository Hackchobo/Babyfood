package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import com.green.babyfood.buy.model.BuyEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품구매")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buy")
public class BuyController {
    private final BuyService SERVICE;

    @PostMapping("/order")
    @Operation(summary = "상품구매",description = "사용법 <br>"+
            "cartId : 장바구니 PK 번호<br>"+
            "productId : 상품 PK 번호<br>"+
            "iuser : 유저 PK 번호 <br>"+
            "count : 상품 갯수 <br>"+
            "totalPrice : 주문한 상품의 가격 <br>"+
            "point : 포인트 사용 금액 <br>"+
            "payment : 결제방법 <br>"+
            "무통장입금(0)/카드(1)/계좌이체(2) <br>"+
            "request: 요청사항 <br>"+
            "receiver: 수령인 <br>")
    public Long BuyProduct(@RequestBody BuyEntity entity){
        return SERVICE.BuyProduct(entity);
    }
    @GetMapping("/point")
    public BuyPoint GetPoint(@RequestParam Long iuser){
        return SERVICE.point(iuser);
    }



}
