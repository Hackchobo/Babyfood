package com.green.babyfood.admin.model;

import lombok.Data;

@Data
public class AdminProductUpdDto {
    int productId;
    String title;
    String name;
    int price;
    int quantity;
    String description;
    int saleVolume;
    int allergy;
}
