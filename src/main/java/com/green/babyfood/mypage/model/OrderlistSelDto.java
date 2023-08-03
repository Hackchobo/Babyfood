package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistSelDto {
    private Long orderId;
    private String createdAt;
    private String thumbnail;
    private String name;
    private int price;
    private String shipment;

}
