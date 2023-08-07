package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyMapper {
    int InsBuy(BuyInsDto dto);
    int InsBuyDetail(BuyDetailInsDto dto);

    int delOrderbasket(Long cartId);
    int updProduct(BuyUpdDto dto);

    int addpoint(BuyUpdPointDto dto);
    int removepoint(BuyUpdPointDto dto);


    BuySelquantityDto quantity(Long productId);
    BuyPoint point(Long iuser);


}
