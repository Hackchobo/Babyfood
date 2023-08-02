package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyInsDto {
    private Long orderId;
    private Long iuser;
    private int payment;
    private int shipment;
    private String call_user;
    private String request;
    private String reciever;

}
