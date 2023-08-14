package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class OrderlistUserDto {
    private String reciever;
    private String address;
    private String addressDetail;
    private String phoneNm;
    private String request;
    private int usepoint;
}
