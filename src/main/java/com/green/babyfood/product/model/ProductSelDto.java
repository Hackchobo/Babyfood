package com.green.babyfood.product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSelDto {
    String title;
    String name;
    String price;
    String quantity;
    String description;
    String allergy;
    int step;
    List<String> img =new ArrayList<>();
    List<String> thumbnail =new ArrayList<>();
}
