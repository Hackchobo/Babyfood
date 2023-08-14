package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistCountSelDto {
    private Long orderId;
    private Long productId;
    private String createdAt;
    private String thumbnail;
    private String name;
    private int price;
    private String shipment;
    private int count;

}
