package com.green.babyfood.orderbasket;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketEntity;
import com.green.babyfood.orderbasket.model.OrderBasketSelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderBasketService {

    private final OrderBasketMapper mapper;
    private final AuthenticationFacade USERPK;

    public Long insOrderBasket(OrderBasketDto dto){
        OrderBasketEntity entity=new OrderBasketEntity();
        entity.setIuser(USERPK.getLoginUserPk());
        entity.setProductId(dto.getProductId());
        entity.setCount(dto.getCount());
        int result=0;
        Long aLong = mapper.countUpd(USERPK.getLoginUserPk(), dto.getProductId());
        System.out.println(aLong);
        if(aLong==null){
             result=mapper.insOrderBasket(entity);
        }
        else {
            mapper.updCount(aLong,dto.getCount());
            return aLong;
        }
        if(result==1){
            return entity.getCartId();
        }
        throw new RuntimeException();
    }


    public List<OrderBasketSelVo> selUserOrderBasket(){
        return mapper.selUserOrderBasket(USERPK.getLoginUserPk());
    }

    public int updCountPlus(Long cartId){
        return mapper.updCountPlus(cartId);
    }

    public int updCountMinus(Long cartId){
        return mapper.updCountMinus(cartId);
    }

    public int delOrderBasket(Long cartId){
        return mapper.delOrderBasket(cartId);
    }
}
