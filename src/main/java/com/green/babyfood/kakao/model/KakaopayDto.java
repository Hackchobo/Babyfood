package com.green.babyfood.kakao.model;

import lombok.Data;



@Data
public class KakaopayDto {

    private Long productId;
    private int count;

    private int payment;
    private String phoneNm;
    private String reciever;
    private String request;
    private String address;
    private String addressDetail;
    private String price;
    private int point;
    private int sum;


}
