package com.green.babyfood.product.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProductSelDto {
    private String title;
    private String name;
    private String price;
    private String quantity;
    private String description;
    private String allergy;
    private int step;
    private List<ProductImgDto> img;
    private List<ProductImgDto> thumbnail;
}
