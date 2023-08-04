package com.green.babyfood.admin.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    int category;
    List cateDetail = new ArrayList<>();

}
