package com.green.babyfood.product.model;

import lombok.Data;

@Data
public class ProductReviewDto {
    private Long iuser;
    private Long productId;
    private String ctnt;
}
