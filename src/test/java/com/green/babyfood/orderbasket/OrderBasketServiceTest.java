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
import static org.mockito.ArgumentMatchers.any;
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


//      OrderBasketEntity entity = new OrderBasketEntity();
//      entity.setIuser(dto.getIuser());
//      entity.setProductId(dto.getProductId());
//      entity.setCount(dto.getCount());
//      int result = 0;
//      Long aLong = mapper.countUpd(dto.getIuser(), dto.getProductId());
//      System.out.println(aLong);
//      if (aLong == null) {
//         result = mapper.insOrderBasket(entity);
//      } else {
//         mapper.updCount(aLong, dto.getCount());
//         return aLong;
//      }
//      if (result == 1) {
//         return entity.getCartId();
//      }
//      throw new RuntimeException();
//   }
   }
 }