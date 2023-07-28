package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class SelOrderlistDto {
    private Long orderId;
    private String title;
    private String name;
    private String count;
    private int price;
    private String shipment;

}
