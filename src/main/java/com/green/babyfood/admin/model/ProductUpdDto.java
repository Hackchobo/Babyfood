package com.green.babyfood.admin.model;

import lombok.Data;

@Data
public class ProductUpdDto {

    private Long productId;
    private String title;
    private String name;
    private int price;
    private int quantity;
    private String description;
    private int allergy;
}
