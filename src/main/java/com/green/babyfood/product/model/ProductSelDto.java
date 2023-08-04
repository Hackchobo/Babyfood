package com.green.babyfood.product.model;

import lombok.Data;

@Data
public class ProductSelDto {
    String title;
    String name;
    String price;
    String quantity;
    String description;
    String allergy;
    String step;
}
