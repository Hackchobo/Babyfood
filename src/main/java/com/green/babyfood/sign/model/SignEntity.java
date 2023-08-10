package com.green.babyfood.sign.model;

import lombok.Data;

import java.util.Date;

@Data
public class SignEntity {
    private String email;
    private String password;
    private String name;
    private String mobileNb;
//    private String role;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String nickNm;
    private String birthday;
}
