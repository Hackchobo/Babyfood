package com.green.babyfood.mypage;


import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.mypage.model.OrderlistDetailSelDto;
import com.green.babyfood.mypage.model.OrderlistSelUserDto;
import com.green.babyfood.mypage.model.OrderlistUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({MypageService.class, AuthenticationFacade.class})
class MypageServiceTest {

    @MockBean
    private MypageMapper mapper;

    @Autowired
    private MypageService service;

    @MockBean
    private AuthenticationFacade USERPK;

    @MockBean
    private PasswordEncoder PW_ENCODER;

    @Test
    void orderlist() {
    }

    @Test
    void orderlistDetail() {
        OrderlistUserDto user = new OrderlistUserDto();
        user.setReciever("수령인1");
        user.setAddress("주소1");
        user.setAddressDetail("상세주소");
        user.setPhoneNm("01025996521");
        user.setRequest("요청사항 없음");

        List<OrderlistDetailSelDto> orderlist =new ArrayList<>();


        OrderlistDetailSelDto orderlistdto = new OrderlistDetailSelDto();

        orderlistdto.setProductId(1L);
        orderlistdto.setIuser(1L);
        orderlistdto.setThumbnail("creampie.png");
        orderlistdto.setCreatedAt("2023-08-13");
        orderlistdto.setName("강민숙");
        orderlistdto.setPrice(2000);
        orderlistdto.setCount(3);
        orderlistdto.setTotalPrice(6000);

        OrderlistDetailSelDto orderlistdto2 = new OrderlistDetailSelDto();

        orderlistdto2.setProductId(2L);
        orderlistdto2.setIuser(2L);
        orderlistdto2.setThumbnail("beefstew.png");
        orderlistdto2.setCreatedAt("2023-08-13");
        orderlistdto2.setName("강민숙");
        orderlistdto2.setPrice(1000);
        orderlistdto2.setCount(3);
        orderlistdto2.setTotalPrice(3000);

        orderlist.add(orderlistdto);
        orderlist.add(orderlistdto2);
        OrderlistSelUserDto dto = new OrderlistSelUserDto();

        dto.setOrderlist(orderlist);
        dto.setUser(user);

        when(mapper.orderlistDetail(any())).thenReturn(dto.getOrderlist());
        OrderlistSelUserDto selUserDto = service.OrderlistDetail(USERPK.getLoginUserPk());

        assertEquals(selUserDto.getOrderlist().get(0).getName(),dto.getOrderlist().get(0).getName());

        verify(mapper).orderlistDetail(anyLong());

    }

    @Test
    void delorder() {
    }

    @Test
    void profile() {
    }

    @Test
    void updProfileDto() {
    }

    @Test
    void nicknmcheck() {
    }

    @Test
    void delUser() {
    }

    @Test
    void updPicUser() {
    }
}