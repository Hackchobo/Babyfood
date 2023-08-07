package com.green.babyfood.product.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductSelDto {
    String title;
    String name;
    String price;
    String quantity;
    String description;
    String allergy;
    int step;
    List<ProductImgDto> img;
    List<ProductImgDto> thumbnail;
}
