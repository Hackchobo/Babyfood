package com.green.babyfood.mypage.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ProfileUpdDto {
    private String nickNm;
    private String password;
    private String phoneNumber;
    private String name;
    private String birthday;
    private String zipcode;
    private String address;
    private String addressDetail;
}
