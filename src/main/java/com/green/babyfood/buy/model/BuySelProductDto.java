package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuySelProductDto {
    private Long productId;
    private String title;
    private String name;
    private int count;
    private int price;
    private String thumbnail;
}
