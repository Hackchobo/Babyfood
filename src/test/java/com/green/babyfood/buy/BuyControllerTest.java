//package com.green.babyfood.buy;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.green.babyfood.buy.model.BuyEntity;
//import com.green.babyfood.buy.model.BuyOrderbasketDto;
//import com.green.babyfood.buy.model.BuyProductRes;
//import com.green.babyfood.config.RedisService;
//import com.green.babyfood.config.security.JwtTokenProvider;
//import com.green.babyfood.config.security.SecurityConfiguration;
//import com.green.babyfood.config.security.model.MyUserDetails;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(BuyController.class)
//@Import({SecurityConfiguration.class, JwtTokenProvider.class})
//class BuyControllerTest {
//
//
//        @Autowired
//        private MockMvc mvc;
//        @Autowired
//        private ObjectMapper objectMapper;
//
//        @MockBean
//        private BuyService service;
//        @MockBean
//        private RedisService redisService;
//
//
//        @BeforeEach
//            //메소드를 static으로 안만들면 에러터짐 테스트실행되기전에 뭔가 실행 시키고 싶을때 @AfterEach 테스트 실행한후
//        void beforeEach() {
//            UserDetails user = createUserDetails(); //박을 것을 얻어온다
//
//            SecurityContext context = SecurityContextHolder.getContext();
//            context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//            //set 한 내용이 박힌다
//        }
//
//        private UserDetails createUserDetails() {
//            List<String> roles = new ArrayList<>();
//            roles.add("ROLE_USER");
//
//            UserDetails userDetails = MyUserDetails.builder()
//                    .iuser(3L)      //실제는 이거랑
//                    //             .name("홍길동")
//                    .roles(roles)   //이것만 박힘
//                    .build();
//            return userDetails;
//        }
//
//        @Test
//        @DisplayName("Buy- 상품구매")
//        void buyProduct() throws Exception {
//            //given
//            BuyEntity entity = new BuyEntity();
//
//            List<BuyOrderbasketDto> orderbasket = new ArrayList<>();
//
//            BuyOrderbasketDto dto = new BuyOrderbasketDto();
//
//            entity.setReceiver("수령인");
//            entity.setAddress("주소1");
//            entity.setAddressDetail("주소2");
//            entity.setPhoneNm("010-1111-1111");
//            entity.setRequest("요청사항");
//            entity.setIuser(1L);
//            entity.setPoint(1000);
//
//            dto.setCartId(1L);
//            dto.setProductId(1L);
//            dto.setIuser(1L);
//            dto.setCount(3);
//            dto.setTotalprice(4500);
//            orderbasket.add(dto);
//
//            BuyProductRes res = new BuyProductRes();
//            res.setOrderId(1L);
//            res.setTotalprice(10000);
//            res.setPoint(2000);
//            res.setPaymentprice(4999);
//
//            given(service.BuyProduct(any())).willReturn(res);
//
//
//            mvc.perform(post("/api/buy/order")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(res)))
//                    .andExpect(status().isOk())
//                    .andDo(print());
//            //then
//            verify(service).BuyProduct(any());
//        }
//
//}