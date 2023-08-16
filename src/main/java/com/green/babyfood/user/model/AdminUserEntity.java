package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class AdminUserEntity {
    private Long iuser;
    private String email;
    private String password;
    private String image;
    private String name;
    private String birthday;
    private String mobileNb;
    private String createdAt;
    private String role;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String nickNm;
    private int point;

}
