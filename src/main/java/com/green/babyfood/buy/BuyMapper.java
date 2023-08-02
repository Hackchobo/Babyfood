package com.green.babyfood.buy;

import com.green.babyfood.buy.model.BuyDetailInsDto;
import com.green.babyfood.buy.model.BuyInsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyMapper {
    int InsBuy(BuyInsDto dto);
    int InsBuyDetail(BuyDetailInsDto dto);
    int delOrderbasket(Long cartId);
}
