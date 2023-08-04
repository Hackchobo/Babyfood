package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyOrderbasketDto {
    private Long cartId;
    private Long productId;
    private Long iuser;
    private int count;
    private int totalprice;
}
