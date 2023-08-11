package com.green.babyfood.orderbasket;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketEntity;
import com.green.babyfood.orderbasket.model.OrderBasketSelVo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({OrderBasketService.class, AuthenticationFacade.class})
class OrderBasketServiceTest {

   @MockBean
   private OrderBasketMapper mapper;

   @Autowired
   private OrderBasketService service;
   @MockBean
   private AuthenticationFacade USERPK;




   @Test
   void insOrderBasket() {

      when(mapper.countUpd(anyLong(),anyLong())).thenReturn(null);
      when(mapper.updCount(anyLong(),anyInt())).thenReturn(1);
      when(mapper.insOrderBasket(any())).thenReturn(1);
      OrderBasketDto dto=new OrderBasketDto();
      dto.setProductId(1L);
      dto.setCount(3);

      OrderBasketEntity entity=new OrderBasketEntity();
      entity.setIuser(10L);
      entity.setProductId(dto.getProductId());
      entity.setCount(dto.getCount());
      Long cartId = service.insOrderBasket(dto);
      Long aLong=mapper.countUpd( 10L,dto.getProductId());
      assertEquals(aLong,cartId);
      int result=0;
      if (aLong == null) {
        result = mapper.insOrderBasket(entity);
      assertEquals(1,result);
      } else {
         int i = mapper.updCount(aLong, dto.getCount());
         assertEquals(1,i);
      }

      verify(mapper, times(2)).countUpd(anyLong(), anyLong());
      verify(mapper, times(2)).insOrderBasket(any());
      verify(mapper, times(0)).updCount(anyLong(),anyInt());
}


   @Test
   void orderBasketSel(){
      List<OrderBasketSelVo> vos=new ArrayList<>();
      OrderBasketSelVo vo1=new OrderBasketSelVo();
      vo1.setCartId(1L);
      vo1.setTitle("타이틀테스트1");
      vo1.setName("네임테스트1");
      vo1.setPrice(10000);
      vo1.setCount(10);
      vo1.setThumbnail("main.pic");
      vo1.setCreatedAt("2023-12-12");

      OrderBasketSelVo vo2=new OrderBasketSelVo();
      vo2.setCartId(2L);
      vo2.setTitle("타이틀테스트2");
      vo2.setName("네임테스트2");
      vo2.setPrice(20000);
      vo2.setCount(20);
      vo2.setThumbnail("main.pic");
      vo2.setCreatedAt("2023-12-12");
      vos.add(vo1);
      vos.add(vo2);

      when(mapper.selUserOrderBasket(anyLong())).thenReturn(vos);
      List<OrderBasketSelVo> rVosList = service.selUserOrderBasket();
      assertEquals(rVosList.get(0).getCartId(),vos.get(0).getCartId());
      assertEquals(rVosList.get(0).getPrice(),vos.get(0).getPrice());
      assertEquals(rVosList.get(0).getName(),vos.get(0).getName());
      assertEquals(rVosList.get(0).getTitle(),vos.get(0).getTitle());
      assertEquals(rVosList.get(0).getThumbnail(),vos.get(0).getThumbnail());
      assertEquals(rVosList.get(0).getCount(),vos.get(0).getCount());

      assertEquals(rVosList.get(1).getCartId(),vos.get(1).getCartId());
      assertEquals(rVosList.get(1).getPrice(),vos.get(1).getPrice());
      assertEquals(rVosList.get(1).getName(),vos.get(1).getName());
      assertEquals(rVosList.get(1).getTitle(),vos.get(1).getTitle());
      assertEquals(rVosList.get(1).getThumbnail(),vos.get(1).getThumbnail());
      assertEquals(rVosList.get(1).getCount(),vos.get(1).getCount());

      verify(mapper).selUserOrderBasket(anyLong());
//     public List<OrderBasketSelVo> selUserOrderBasket(Long iuser){
//        return mapper.selUserOrderBasket(iuser);
//     }
   }


   @Test
   void delOrderBasket(){
      when(mapper.delOrderBasket(anyLong())).thenReturn(10);

      int i = service.delOrderBasket(anyLong());
      assertEquals(10,i);

      verify(mapper).delOrderBasket(anyLong());
   }


}