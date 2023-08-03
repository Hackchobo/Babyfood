package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuySelOrderDto {
    private String thumbnail;
    private String title;
    private String name;
    private String price;
    private int count;
    private String username;
    private String address;
    private String addressDetail;
    private String request;
    private String totalPrice;
}
