package com.green.babyfood.orderbasket;

import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketEntity;
import com.green.babyfood.orderbasket.model.OrderBasketSelVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedNotification;

import java.util.List;

@Mapper
public interface OrderBasketMapper {

    int insOrderBasket(OrderBasketEntity entity);
    List<OrderBasketSelVo> selUserOrderBasket(Long iuser);
    int updCountPlus(Long cartId);
    int updCountMinus(Long cartId);
    int delOrderBasket(Long cartId);
    Long countUpd(Long iuser,Long productId);
    int updCount(Long cartId,int count);
}
