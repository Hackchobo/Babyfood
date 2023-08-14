package com.green.babyfood.mypage.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class OrderlistSelUserDto {
    List<OrderlistDetailSelDto> orderlist;
    OrderlistUserDto user;
}
