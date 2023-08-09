package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistDetailSelDto {

    private Long productId;
    private Long iuser;
    private String thumbnail;
    private String title;
    private String createdAt;
    private String name;
    private int price;
    private int count;
    private int totalPrice;

}
