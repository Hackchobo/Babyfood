package com.green.babyfood.mypage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.SecurityConfiguration;
import com.green.babyfood.config.security.model.MyUserDetails;
import com.green.babyfood.mypage.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MypageController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
@Slf4j
class MypageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MypageService service;

    @MockBean
    private RedisService redisService;

    @BeforeEach
        //메소드를 static으로 안만들면 에러터짐 테스트실행되기전에 뭔가 실행 시키고 싶을때 @AfterEach 테스트 실행한후
    void beforeEach() {
        UserDetails user = createUserDetails(); //박을 것을 얻어온다

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
        //set 한 내용이 박힌다
    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        UserDetails userDetails = MyUserDetails.builder()
                .iuser(3L)      //실제는 이거랑
                //             .name("홍길동")
                .roles(roles)   //이것만 박힘
                .build();
        return userDetails;
    }

    @Test
    void getOrderlistMonths() throws Exception {
//        OrderlistSelDto[] arr = new OrderlistSelDto[2];
//        arr[0].setOrderId(1L);
//        arr[0].setCreatedAt("2023-08-13");
//        arr[0].setThumbnail("creampie.jpg");
//        arr[0].setName("강민숙");
//        arr[0].setPrice(2000);
//        arr[0].setShipment("1");
//
//        arr[1].setOrderId(2L);
//        arr[1].setCreatedAt("2023-08-13");
//        arr[1].setThumbnail("beefstew.png");
//        arr[1].setName("강민숙");
//        arr[1].setPrice(2000);
//        arr[1].setShipment("1");
//
//        given(service.Orderlist(anyInt())).willReturn(arr);
//
//        ResultActions ra = mvc.perform(get("/api/mypage/orderlist"));
//
//        ra.andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(arr.length)))
//                .andExpect(jsonPath("$").value(1L));

    }

    @Test
    void getOrderlistDetail() throws Exception {

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

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String resJson  = om.writeValueAsString(dto);



        given(service.OrderlistDetail(any())).willReturn(dto);
        ResultActions ra = mvc.perform(get("/api/mypage/orderlist/detail")
                .content(resJson)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andDo(print());
        verify(service).OrderlistDetail(any());

    }


    @Test
    void getprofile() throws Exception {
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

        given(service.profile()).willReturn(dto);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String resJson  = om.writeValueAsString(dto);

        ResultActions ra = mvc.perform(get("/api/mypage/profile")
                .content(resJson)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andDo(print());

        verify(service).profile();

    }

    @Test
    void patchprofile() throws Exception {
        ProfileUpdDto dto = new ProfileUpdDto();
        dto.setNickNm("짱좋아");
        dto.setPassword("1234");
        dto.setPhoneNumber("01012344567");
        dto.setName("서영기");
        dto.setBirthday("1998-01-01");
        dto.setZipcode("02985");
        dto.setAddress("대구광역시 중구");
        dto.setAddressDetail("이곡동 래미안아파트 101동 151호");

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String resJson  = om.writeValueAsString(dto);

        given(service.updPicUser(any())).willReturn(1);



        ResultActions ra = mvc.perform(patch("/api/mypage/profile")
                .content(resJson)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andDo(print());

        verify(service).updProfile(any());


    }
    @Test
    void getNickNamecheck() throws Exception {
        String nickname = "짱좋아";
        given(service.nicknmcheck(anyString())).willReturn(1);


        ResultActions ra = mvc.perform(post("/api/mypage/profile/nickname")
                        .param("nickname",nickname)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andDo(print());

        verify(service).nicknmcheck(anyString());

    }


    @Test
    void patchPic() throws Exception{
//        MultipartFile file;
//        given(service.updPicUser(any())).willReturn(1);
//
//        mvc.perform(patch("/api/mypage/profile/pic")
//                .param("file",String.valueOf(file)));
//

    }
}