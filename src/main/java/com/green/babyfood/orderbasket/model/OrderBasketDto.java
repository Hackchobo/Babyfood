package com.green.babyfood.orderbasket.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderBasketDto {

    private Long iuser;
    private Long productId;
    private int count;


}
