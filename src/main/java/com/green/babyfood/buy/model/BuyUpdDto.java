package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyUpdDto {
    private Long productId;
    private int quantity;
    private int saleVolumn;
}
