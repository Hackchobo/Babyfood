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

//   @MockBean
//   private OrderBasketMapper mapper;

//   @Autowired
//   private OrderBasketService service;


//   @Test
//   void insOrderBasket(){
//       when(mapper.insOrderBasket(any())).thenReturn(1);

//       OrderBasketDto dto=new OrderBasketDto();
//       dto.setIuser(1L);
//       dto.setProductId(1L);
//       dto.setCount(3);

//       Long aLong = service.insOrderBasket(dto);

//       assertEquals(0L,aLong);

//       verify(mapper).insOrderBasket(any());

//   }


}