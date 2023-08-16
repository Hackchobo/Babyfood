package com.green.babyfood.buy.model;

import lombok.Data;

@Data
public class BuyProductRes {
    private Long OrderId;
    private int totalprice;
    private int point;
    private int paymentprice;
}
