package com.green.babyfood.orderbasket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderBasketSelVo {


    private Long cartId;
    private Long productId;
    private String title;
    private String name;
    private int count;
    private int price;
    private String thumbnail;
    private String createdAt;
}
