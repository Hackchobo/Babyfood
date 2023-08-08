package com.green.babyfood.product.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class ProductSelDto {
    private String title;
    private String name;
    private String price;
    private String quantity;
    private String description;
    private String allergy;
    private int step;
    private List<String> img;
    private List<String> thumbnail;
//    private String thumbnail;
}
