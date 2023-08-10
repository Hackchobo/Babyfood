package com.green.babyfood.buy;

import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuyOrderbasketDto;
import com.green.babyfood.buy.model.BuyProductRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@Import({BuyService.class})
class BuyServiceTest {

//  @MockBean
//  private BuyMapper mapper;

//  @Autowired
//  private BuyService service;

//  @Test
//  @DisplayName("BuyService- 상품구매")
//  void buyProduct() {
//      //given
//      BuyEntity entity = new BuyEntity();

//      List<BuyOrderbasketDto> orderbasket = new ArrayList<>();

//      BuyOrderbasketDto dto = new BuyOrderbasketDto();
//      BuyOrderbasketDto dto2 = new BuyOrderbasketDto();

//      entity.setReceiver("수령인");
//      entity.setAddress("주소1");
//      entity.setAddressDetail("주소2");
//      entity.setPhoneNm("010-1111-1111");
//      entity.setRequest("요청사항");
//      entity.setIuser(1L);
//      entity.setPoint(1000);

//      dto.setCartId(1L);
//      dto.setProductId(1L);
//      dto.setIuser(1L);
//      dto.setCount(3);
//      dto.setTotalprice(4500);

//      dto2.setCartId(2L);
//      dto2.setProductId(2L);
//      dto2.setIuser(1L);
//      dto2.setCount(1);
//      dto2.setTotalprice(1000);

//      orderbasket.add(dto);
//      orderbasket.add(dto2);

//      entity.setOrderbasket(orderbasket);
//      service.BuyProduct(entity);





//    }
}