package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyInsOrderbasketDto {
    private Long cartId;
    private Long productId;
    private int count;
    private int totalprice;
}
