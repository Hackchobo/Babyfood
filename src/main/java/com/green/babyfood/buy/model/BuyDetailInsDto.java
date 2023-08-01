package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyDetailInsDto {

    private Long orderId;
    private Long productId;
    private int count;
    private int totalPrice;
}
