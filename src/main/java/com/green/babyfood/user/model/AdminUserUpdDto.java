package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class AdminUserUpdDto {
    private String email;
    private String name;
    private String birthday;
    private String mobileNb;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String nickNm;
}
