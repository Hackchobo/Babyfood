package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class UserInsDto {
    private String email;
    private String nickNm;
    private String password;
    private String secret;
    private String name;
    private String mobileNb;
    private String birthday;
    private String address;

}
