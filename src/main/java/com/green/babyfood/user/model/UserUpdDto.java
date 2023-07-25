package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class UserUpdDto {
    private Long iuser;
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String mobileNb;
    private String address;
    private String nickNm;
}
