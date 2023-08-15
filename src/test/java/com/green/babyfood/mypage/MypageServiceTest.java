package com.green.babyfood.mypage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.mypage.model.*;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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
        assertEquals(selUserDto.getOrderlist().get(0).getThumbnail(),dto.getOrderlist().get(0).getThumbnail());
        assertEquals(selUserDto.getOrderlist().get(0).getProductId(),dto.getOrderlist().get(0).getProductId());
        assertEquals(selUserDto.getOrderlist().get(0).getIuser(),dto.getOrderlist().get(0).getIuser());
        assertEquals(selUserDto.getOrderlist().get(0).getCount(),dto.getOrderlist().get(0).getCount());
        assertEquals(selUserDto.getOrderlist().get(0).getCreatedAt(),dto.getOrderlist().get(0).getCreatedAt());
        assertEquals(selUserDto.getOrderlist().get(0).getTotalPrice(),dto.getOrderlist().get(0).getTotalPrice());
        assertEquals(selUserDto.getOrderlist().get(0).getIuser(),dto.getOrderlist().get(0).getIuser());

        assertEquals(selUserDto.getOrderlist().get(1).getName(),dto.getOrderlist().get(1).getName());
        assertEquals(selUserDto.getOrderlist().get(1).getThumbnail(),dto.getOrderlist().get(1).getThumbnail());
        assertEquals(selUserDto.getOrderlist().get(1).getProductId(),dto.getOrderlist().get(1).getProductId());
        assertEquals(selUserDto.getOrderlist().get(1).getIuser(),dto.getOrderlist().get(1).getIuser());
        assertEquals(selUserDto.getOrderlist().get(1).getCount(),dto.getOrderlist().get(1).getCount());
        assertEquals(selUserDto.getOrderlist().get(1).getCreatedAt(),dto.getOrderlist().get(1).getCreatedAt());
        assertEquals(selUserDto.getOrderlist().get(1).getTotalPrice(),dto.getOrderlist().get(1).getTotalPrice());
        assertEquals(selUserDto.getOrderlist().get(1).getIuser(),dto.getOrderlist().get(1).getIuser());

        verify(mapper).orderlistDetail(anyLong());

    }

    @Test
    void delorder() {


    }

    @Test
    void profile() {
        ProfileSelDto dto = new ProfileSelDto();
        dto.setIuser(1L);
        dto.setEmail("ob09@naver.com");
        dto.setImage("Image.png");
        dto.setName("강민숙");
        dto.setMobileNb("010-1111-1111");
        dto.setBirthday("1999-01-01");
        dto.setZipcode("11111111");
        dto.setAddress("주소1");
        dto.setAddressDetail("주소2");
        dto.setNickNm("별명1");
        dto.setPoint(1000);

        when(mapper.profile(any())).thenReturn(dto);

        ProfileSelDto profile = service.profile();

        assertEquals(profile.getIuser(),dto.getIuser());
        assertEquals(profile.getEmail(),dto.getEmail());
        assertEquals(profile.getImage(),dto.getImage());
        assertEquals(profile.getName(),dto.getName());
        assertEquals(profile.getBirthday(),dto.getBirthday());
        assertEquals(profile.getZipcode(),dto.getZipcode());
        assertEquals(profile.getAddress(),dto.getAddress());
        assertEquals(profile.getAddressDetail(),dto.getAddressDetail());
        assertEquals(profile.getNickNm(),dto.getNickNm());
        assertEquals(profile.getPoint(),dto.getPoint());

        verify(mapper).profile(any());


    }



    @Test
    void updProfile() {
        ProfileUpdDto dto = new ProfileUpdDto();
        dto.setNickNm("짱좋아");
        dto.setPassword("1234");
        dto.setPhoneNumber("01012344567");
        dto.setName("서영기");
        dto.setBirthday("1998-01-01");
        dto.setZipcode("02985");
        dto.setAddress("대구광역시 중구");
        dto.setAddressDetail("이곡동 래미안아파트 101동 151호");



        when(mapper.Updprofile(any())).thenReturn(1);

        int result = service.updProfile(dto);

        assertEquals(1,result);

        verify(mapper).Updprofile(any());

    }

    @Test
    void nicknmcheck() {
        when(mapper.SelNickNm(any())).thenReturn("짱좋아");

        String nickname = "짱좋아";

        int nicknmcheck = service.nicknmcheck(nickname);

        if (nickname.equals("짱좋아")){
            assertEquals(1,nicknmcheck);
        }else
            assertEquals(0,nicknmcheck);

        verify(mapper).SelNickNm(any());


    }

        @Test
        void updPicUser () {
        }

}