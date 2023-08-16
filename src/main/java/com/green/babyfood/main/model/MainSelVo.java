package com.green.babyfood.main.model;

import lombok.Data;

@Data
public class MainSelVo {

    private Long productId;
    private String thumbnail;
//    private String title;
    private String name;
    private int price;
    private int quantity;
    private int volumn;

}
