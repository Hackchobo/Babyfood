package com.green.babyfood.orderbaskettest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.SecurityConfiguration;
import com.green.babyfood.config.security.model.MyUserDetails;
import com.green.babyfood.orderbasket.OrderBasketController;
import com.green.babyfood.orderbasket.OrderBasketService;
import com.green.babyfood.orderbasket.model.OrderBasketDto;
import com.green.babyfood.orderbasket.model.OrderBasketSelVo;
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


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderBasketController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
public class OrderBasketControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderBasketService service;
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
    @DisplayName("장바구니 등록")
    void orderBasketIns() throws Exception {
        OrderBasketDto dto=new OrderBasketDto();
        dto.setIuser(1L);
        dto.setProductId(1L);
        dto.setCount(2);


        given(service.insOrderBasket(any())).willReturn(10L);

        mvc.perform(post("/api/orderbasket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(service).insOrderBasket(any());

    }



    @Test
    void orerBasketSel() throws Exception {
        List<OrderBasketSelVo> list=new ArrayList<>();
        list.add(new OrderBasketSelVo(1L,1L,"테스트1","홍길동1",3,10000,"main.jpg","2023-01-01 12:12:12"));
        list.add(new OrderBasketSelVo(2L,2L,"테스트2","홍길동2",3,10000,"main.jpg","2023-01-01 12:12:12"));

        given(service.selUserOrderBasket(any())).willReturn(list);

        ResultActions ra = mvc.perform(get("/api/orderbasket"));


        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]",hasSize(list.size())))
                .andExpect(jsonPath("$.[0].cartId").value(1L))
                .andExpect(jsonPath("$.[0].productId").value(1L))
                .andExpect(jsonPath("$.[0].title").value("테스트1"))
                .andExpect(jsonPath("$.[0].name").value("홍길동1"))
                .andExpect(jsonPath("$.[1].cartId").value(2L))
                .andExpect(jsonPath("$.[1].productId").value(2L))
                .andExpect(jsonPath("$.[1].title").value("테스트2"))
                .andExpect(jsonPath("$.[1].name").value("홍길동2"))
                .andDo(print());

        verify(service).selUserOrderBasket(any());
    }
}
