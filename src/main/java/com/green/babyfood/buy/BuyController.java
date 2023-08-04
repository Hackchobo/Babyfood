package com.green.babyfood.buy;

import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuySelOrderDto;
import com.green.babyfood.buy.model.BuySelOrderlistDto;
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
            "준비중(1)/배송중(2)/배송완료(0)/주문취소(3) <br>"+
            "request: 요청사항 <br>"+
            "receiver: 수령인 <br>")
    public Long BuyProduct(@RequestBody BuyEntity entity){
        return SERVICE.BuyProduct(entity);
    }


    @GetMapping("/orderlist")
    @Operation(summary = "상품조회",description = ""+
            "orderId : 상품 PK 번호<br>" +
            "1~4까지 있음!")
    public BuySelOrderlistDto GetBuyProduct(int orderId){
        return SERVICE.selorderproduct(orderId);
    }
}
