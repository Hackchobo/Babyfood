package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class ProfileEntity {
    private Long iuser;
    private String nickNm;
    private String password;
    private String phoneNumber;
    private String name;
    private String birthday;
    private String zipcode;
    private String address;
    private String addressDetail;
}
