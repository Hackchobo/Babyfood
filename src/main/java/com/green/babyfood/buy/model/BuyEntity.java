package com.green.babyfood.buy.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyEntity {
    private String receiver;
    private String address;
    private String addressDetail;
    private String phoneNm;
    private String request;
    private int payment;
    private Long iuser;
    private int point;
    List<BuyOrderbasketDto> orderbasket;
}
