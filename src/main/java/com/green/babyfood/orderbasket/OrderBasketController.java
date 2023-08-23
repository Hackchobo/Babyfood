package com.green.babyfood.orderbasket;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketSelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/orderbasket")
@RequiredArgsConstructor
public class OrderBasketController {

    private final OrderBasketService service;


    @PostMapping
    @Operation(summary = "상품 페이지에서 장바구니 버튼",description = ""+
    "productId : 상품번호<br>"+
    "count : 수량")
    public Long post(@RequestBody OrderBasketDto dto){
        return service.insOrderBasket(dto);
    }


    @GetMapping
    @Operation(summary = "장바구니 목록",description = ""+
            "cartId : 장바구니의 번호<br>"+
            "productId : 상품의 번호<br>"+
            "title : 상품의 title<br>"+
            "name : 상품의 이름<br>"+
            "count : 수량 <br>"+
            "price : 해당 상품 하나의 가격<br>"+
            "thumbnail : 해당 상품의 썸네일<br>"+
            "createdAt : 상품이 장바구니에 담긴 날짜"
    )
    public List<OrderBasketSelVo> getUserOrderBasket(){
        return service.selUserOrderBasket();
    }

    @PatchMapping("/plus")
    @Operation(summary = "count 플러스 버튼")
    public int patchCountPlus(Long cartId){
        return service.updCountPlus(cartId);
    }
    @PatchMapping("/minus")
    @Operation(summary = "count 마이너스 버튼")
    public int patchCountMinus(Long cartId){
        return service.updCountMinus(cartId);
    }

    @DeleteMapping
    @Operation(summary = "장바구니 삭제",description = ""+
    "cartId : 장바구니 번호")
    private int delOrderBasket(Long cartId){
        return service.delOrderBasket(cartId);
    }



}
