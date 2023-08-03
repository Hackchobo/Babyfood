package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyEntity {
    private Long cartId;
    private Long productId;
    private Long iuser;
    private int count;
    private int totalPrice;
    private int point;
    private int payment;
    private String calluser;
    private String request;
    private String receiver;
}
