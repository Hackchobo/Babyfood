package com.green.babyfood.buy.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BuySelOrderlistDto {
    List<BuySelOrderDto> order;
    BuySelUserDto user;

}
