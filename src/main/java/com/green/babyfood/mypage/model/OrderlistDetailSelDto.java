package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistDetailSelDto {
    private Long orderId;
    private Long productId;
    private String thumbnail;
    private String reciever;
    private String request;
    private String createdAt;
    private String title;
    private String name;
    private int count;
    private int price;
    private int shipment;

}
