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
            "count : 상품 갯수 <br>"+
            "totalPrice : 주문한 상품의 가격 <br>"+
            "point : 포인트 사용 금액 <br>"+
            "payment : 결제방법 <br>"+
            "무통장입금(0)/카드(1)/계좌이체(2) <br>"+
            "request: 요청사항 <br>"+
            "receiver: 수령인 <br>"+
            " 제품의 수량이 없거나 오류가 나면 return null <br>"+
            " 결제가 완료 되면 주문내역의 PK 값과 사용자가 사용한 point 를 return 시켜줌<br>" +
            "totalprice: 상품들의 총 가격"+
            "point: 포인트 사용금액<br>"+
            "paymentprice: 결제한 금액<br>"+
            "orderId: 주문번호 PK<br>"
    )
    public BuyProductRes BuyProduct(@RequestBody BuyInsDto dto){
        return SERVICE.BuyProduct(dto);
    }

    @PostMapping("/order/Id")
    public BuyProductRes BuyProductId(@RequestBody BuyInsIdDto dto){
        return SERVICE.BuyIdProduct(dto);
    }

}
