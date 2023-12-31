package com.green.babyfood.orderbasket;

import com.green.babyfood.orderbasket.model.OrderBasketEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderBasketMapperTest {

    @Autowired
    private OrderBasketMapper mapper;

     @Test
     void insOrderBasket(){
         OrderBasketEntity entity=new OrderBasketEntity();
         entity.setIuser(1L);
         entity.setProductId(1L);
         entity.setCount(3);

         mapper.insOrderBasket(entity);
         assertEquals(9L,entity.getCartId());

       }
     @Test
    void delOrderBasket(){
         int i = mapper.delOrderBasket(7L);
         assertEquals(1,i);
     }
}