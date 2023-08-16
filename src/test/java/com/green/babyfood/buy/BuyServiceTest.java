package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import com.green.babyfood.config.security.AuthenticationFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({BuyService.class})
class BuyServiceTest {

  @MockBean
  private BuyMapper mapper;

  @Autowired
  private BuyService service;
    @MockBean
    private AuthenticationFacade USERPK;

  @Test
  @DisplayName("BuyService- 상품구매")
  void buyProduct() {
      //given
      BuyInsDto insDto = new BuyInsDto();


      insDto.setReceiver("수령인");
      insDto.setAddress("주소1");
      insDto.setAddressDetail("주소2");
      insDto.setPhoneNm("01011111111");
      insDto.setRequest("요청사항");
      insDto.setPoint(1000);
      List<BuyInsOrderbasketDto> list = new ArrayList();
      list.add(new BuyInsOrderbasketDto(1L,1L,3,3000));
      list.add(new BuyInsOrderbasketDto(1L,1L,3,3000));
      insDto.setInsorderbasket(list);

      BuyUserSelDto dto = new BuyUserSelDto();
      dto.setAddress("주소1");
      dto.setName("아무개");
      dto.setAddressDetail("주소2");
      dto.setMobileNm("01011111111");

      BuySelquantityDto quantity = new BuySelquantityDto();
      quantity.setProductId(1L);
      quantity.setQuantity(20);

      int totalprice = 0;
      for (int i = 0; i <insDto.getInsorderbasket().size(); i++) {
          totalprice += insDto.getInsorderbasket().get(i).getTotalprice();
      }
      int paymentprice = totalprice - insDto.getPoint();

      //when
      when(mapper.InsBuy(any())).thenReturn(1); //파라미터타입 ,
      when(mapper.InsBuyDetail(any())).thenReturn(1);
      when(mapper.delOrderbasket(anyLong())).thenReturn(1);
      when(mapper.updProduct(any())).thenReturn(1);
      when(mapper.selUser(anyLong())).thenReturn(dto);
      when(mapper.addpoint(any())).thenReturn(1);
      when(mapper.removepoint(any())).thenReturn(1);
      when(mapper.quantity(any())).thenReturn(quantity);

      BuyProductRes buyProductRes = service.BuyProduct(insDto);
      buyProductRes.setOrderId(1L);
      assertEquals(1L,buyProductRes.getOrderId());
      assertEquals(totalprice,buyProductRes.getTotalprice());
      assertEquals(insDto.getPoint(),buyProductRes.getPoint());
      assertEquals(paymentprice,buyProductRes.getPaymentprice());

      verify(mapper,times(1)).InsBuy(any());
      verify(mapper,times(2)).updProduct(any());
      verify(mapper,times(2)).InsBuyDetail(any());
      verify(mapper,times(2)).delOrderbasket(any());
      verify(mapper,times(2)).updProduct(any());
      verify(mapper,times(1)).selUser(any());
      verify(mapper,times(1)).addpoint(any());
      verify(mapper,times(1)).removepoint(any());
      verify(mapper,times(2)).quantity(any());

}
}