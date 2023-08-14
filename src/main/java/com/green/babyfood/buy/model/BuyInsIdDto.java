package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyInsIdDto {
    private String receiver;
    private String address;
    private String addressDetail;
    private String phoneNm;
    private String request;
    private int payment;
    private int point;

    private Long cartId;
    private Long productId;
    private int count;
    private int totalprice;
}
