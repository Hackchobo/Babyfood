package com.green.babyfood.mypage.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderlistSelUserDto {
    List<OrderlistDetailSelDto> orderlist;
    OrderlistUserDto user;
}
