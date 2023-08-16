package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyInsorder {
    private Long orderId;
    private Long iuser;
    private int payment;
    private int shipment;
    private String phoneNm;
    private String request;
    private String receiver;
    private String address;
    private String addressDetail;
    private int point;
}
