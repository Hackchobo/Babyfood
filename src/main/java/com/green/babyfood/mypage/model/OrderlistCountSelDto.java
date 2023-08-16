package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistCountSelDto {
    private Long orderId;
    private Long productId;
    private String thumbnail;
    private String title;
    private String name;
    private int price;
    private String shipment;
    private int count;
    private int cateId;

    private String createdAt;
    private Long iuser;
    private int totalPrice;



}
