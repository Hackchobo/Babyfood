package com.green.babyfood.product.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductBuyDto {
    private String title;
    private String name;
    private String price;
    private String quantity;
}
