package com.green.babyfood.product.model;

import lombok.Data;

@Data
public class ProductReviewEntity {
    private Long iuser;
    private String ctnt;
    private Long productId;
}
