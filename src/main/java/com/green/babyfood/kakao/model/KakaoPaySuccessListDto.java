package com.green.babyfood.kakao.model;

import lombok.Data;

import java.util.List;

@Data
public class KakaoPaySuccessListDto {

    private Long iuser;
    private Long productId;
    private int count;
    private int payment;
    private String phoneNm;
    private String reciever;
    private String request;
    private String address;
    private String addressDetail;
    private int point;
    private int totalPrice;
    private int orderId;
}
