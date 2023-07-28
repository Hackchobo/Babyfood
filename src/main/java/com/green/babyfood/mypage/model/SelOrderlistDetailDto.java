package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class SelOrderlistDetailDto {
    private Long orderId;
    private String reciever;
    private String request;
    private String createdAt;
    private String title;
    private String name;
    private String count;
    private int price;
    private String shipment;

}
