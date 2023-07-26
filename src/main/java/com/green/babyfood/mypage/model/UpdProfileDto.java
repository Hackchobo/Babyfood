package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class UpdProfileDto {
    private int iuser;
    private String nickNm;
    private String password;
    private String passwordcheck;
    private String phoneNumber;
    private String birthday;
    private String address;
    private String addressDetail;
}
