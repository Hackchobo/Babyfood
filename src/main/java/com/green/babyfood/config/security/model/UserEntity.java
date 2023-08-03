package com.green.babyfood.config.security.model;

import lombok.Data;

@Data
public class UserEntity {
    private Long iuser;
    private String email;
    private String password;
    private String img;
    private String name;
    private String birthday;
    private String mobileNb;
    private String createdAt;
    private String role;
    private String address;
    private String addressDetail;
    private String nickNm;
    private int point;
}
