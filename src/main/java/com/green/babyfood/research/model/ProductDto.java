package com.green.babyfood.research.model;

import lombok.Data;

@Data
public class ProductDto {
    private int productid;
    private String title;
    private String name;
    private int price;
    private int quantity;
    private String description;
    private int salevolumn;
    private String allergy;
}
