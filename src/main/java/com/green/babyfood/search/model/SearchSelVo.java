package com.green.babyfood.search.model;

import lombok.Data;

@Data
public class SearchSelVo {
    private int productid;
    private String name;
    private String thumbnail;
    private int price;
    private String cateId;
}
