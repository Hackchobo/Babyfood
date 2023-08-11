package com.green.babyfood.buy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyInsOrderbasketDto {
    private Long cartId;
    private Long productId;
    private int count;
    private int totalprice;
}
