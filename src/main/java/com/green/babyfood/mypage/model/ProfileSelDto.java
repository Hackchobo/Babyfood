package com.green.babyfood.mypage.model;

import lombok.Data;

@Data
public class ProfileSelDto {
    private String email;
    private String image;
    private String name;
    private String birthday;
    private String mobileNb;
    private String address;
    private String addressDetail;
    private String nickNm;
    private int point;
}
