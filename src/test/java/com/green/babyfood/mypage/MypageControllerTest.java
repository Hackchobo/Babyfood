//package com.green.babyfood.mypage;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.green.babyfood.config.RedisService;
//import com.green.babyfood.config.security.JwtTokenProvider;
//import com.green.babyfood.config.security.SecurityConfiguration;
//import com.green.babyfood.config.security.model.MyUserDetails;
//import com.green.babyfood.mypage.model.OrderlistSelDto;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
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
//
//@WebMvcTest(MypageController.class)
//@Import({SecurityConfiguration.class, JwtTokenProvider.class})
//@Slf4j
//class MypageControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @MockBean
//    private MypageService service;
//
//    @MockBean
//    private RedisService redisService;
//
//    @BeforeEach
//        //메소드를 static으로 안만들면 에러터짐 테스트실행되기전에 뭔가 실행 시키고 싶을때 @AfterEach 테스트 실행한후
//    void beforeEach() {
//        UserDetails user = createUserDetails(); //박을 것을 얻어온다
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//        //set 한 내용이 박힌다
//    }
//
//    private UserDetails createUserDetails() {
//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_USER");
//
//        UserDetails userDetails = MyUserDetails.builder()
//                .iuser(3L)      //실제는 이거랑
//                //             .name("홍길동")
//                .roles(roles)   //이것만 박힘
//                .build();
//        return userDetails;
//    }
//
//    @Test
//    void getOrderlistMonths() {
//        OrderlistSelDto[] getOrderlistMonth ;
//
//    }
//
//    @Test
//    void getOrderlistDetail() {
//    }
//
//
//    @Test
//    void getprofile() {
//    }
//
//    @Test
//    void patchprofile() {
//
//    }
//
//
//    @Test
//    void patchPic() {
//    }
//}