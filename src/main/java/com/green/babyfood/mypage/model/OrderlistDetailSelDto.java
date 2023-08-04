package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistDetailSelDto {

    private Long productId;
    private String thumbnail;
    private String title;
    private String name;
    private String price;
    private String count;
    private String totalPrice;

}
