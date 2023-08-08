package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyProductRes {
    private Long OrderId;
    private int point;
    private int totalprice;
    private int paymentprice;
}
