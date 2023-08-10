package com.green.babyfood.orderbasket;

import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({OrderBasketService.class})
class OrderBasketServiceTest {

   @MockBean
   private OrderBasketMapper mapper;

   @Autowired
   private OrderBasketService service;


   @Test
   void insOrderBasket() {
      when(mapper.countUpd(anyLong(),anyLong())).thenReturn(10L);
      when(mapper.updCount(anyLong(),anyInt())).thenReturn(1);
      when(mapper.insOrderBasket(any())).thenReturn(1);
      OrderBasketDto dto=new OrderBasketDto();
      dto.setIuser(1L);
      dto.setProductId(1L);
      dto.setCount(3);
      Long cartId = service.insOrderBasket(dto);
      OrderBasketEntity entity=new OrderBasketEntity();
      entity.setIuser(dto.getIuser());
      entity.setProductId(dto.getProductId());
      entity.setCount(dto.getCount());

      Long aLong=mapper.countUpd(dto.getIuser(),dto.getProductId());
      assertEquals(aLong,cartId);
      int result=0;
      if (aLong == null) {
        result = mapper.insOrderBasket(entity);
      assertEquals(1,result);
      } else {
         int i = mapper.updCount(aLong, dto.getCount());
         assertEquals(1,i);
      }





//     OrderBasketEntity entity = new OrderBasketEntity();
//     entity.setIuser(dto.getIuser());
//     entity.setProductId(dto.getProductId());
//     entity.setCount(dto.getCount());
//     int result = 0;
//     Long aLong = mapper.countUpd(dto.getIuser(), dto.getProductId());
//     System.out.println(aLong);
//     if (aLong == null) {
//        result = mapper.insOrderBasket(entity);
//     } else {
//        mapper.updCount(aLong, dto.getCount());
//        return aLong;
//     }
//     if (result == 1) {
//        return entity.getCartId();
//     }
//     throw new RuntimeException();
//  }
}
 }