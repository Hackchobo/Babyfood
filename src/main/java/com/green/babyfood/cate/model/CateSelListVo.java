package com.green.babyfood.cate.model;

import lombok.Data;

@Data
public class CateSelListVo {

    private Long productId;
    private String thumbnail;
//    private String title;
    private String name;
    private int price;
    private int quantity;
    private int volumn;

}
