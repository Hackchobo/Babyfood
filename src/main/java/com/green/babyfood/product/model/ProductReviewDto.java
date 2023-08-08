package com.green.babyfood.product.model;

import lombok.Data;

@Data
public class ProductReviewDto {
    private int iuser;
    private int productId;
    private String ctnt;
}
