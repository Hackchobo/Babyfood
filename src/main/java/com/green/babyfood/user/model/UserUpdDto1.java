package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class UserUpdDto1 {
    private Long iuser;
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String mobileNb;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String nickNm;
}
