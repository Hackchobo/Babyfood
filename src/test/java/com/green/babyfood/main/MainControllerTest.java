package com.green.babyfood.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.SecurityConfiguration;
import com.green.babyfood.config.security.model.MyUserDetails;
import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;
import com.green.babyfood.orderbasket.OrderBasketController;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.function.RequestPredicates;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;


@WebMvcTest(MainController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
@Slf4j
class MainControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MainService service;
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
    void selPaging() throws Exception {

        MainSelVoMaxPaige mainSelVoMaxPaige=new MainSelVoMaxPaige();
        mainSelVoMaxPaige.setMaxPage(10);
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);

        MainSelVo mainSelVo2=new MainSelVo();
        mainSelVo2.setProductId(2L);
        mainSelVo2.setTitle("테스트2");
        mainSelVo2.setName("네임테스트2");
        mainSelVo2.setPrice(2000);
        mainSelVo2.setQuantity(20);
        mainSelVo2.setThumbnail("main.pic2");
        mainSelVo2.setVolumn(200);
        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);
        list.add(mainSelVo2);
        mainSelVoMaxPaige.setList(list);

        given(service.mainSelView(anyInt(),anyInt())).willReturn(mainSelVoMaxPaige);

        ResultActions ra = mvc.perform(get("/api/main").param("page","10").param("row","10"));

        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.list.[*]",hasSize(list.size())))
                .andExpect(jsonPath("$.list.[0].productId").value(1L))
                .andExpect(jsonPath("$.list.[0].title").value("테스트1"))
                .andExpect(jsonPath("$.list.[0].name").value("네임테스트1"))
                .andExpect(jsonPath("$.list.[0].price").value(1000))
                .andExpect(jsonPath("$.list.[0].quantity").value(10))
                .andExpect(jsonPath("$.list.[0].thumbnail").value("main.pic1"))
                .andExpect(jsonPath("$.list.[0].volumn").value(100))
                .andExpect(jsonPath("$.maxPage").value(10))
                .andDo(print());

        verify(service).mainSelView(anyInt(),anyInt());
    }

    @Test
    void bestSell() throws Exception {
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);

       List<MainSelVo> list=new ArrayList<>();
       list.add(mainSelVo1);
       given(service.bestSell()).willReturn(list);

        ResultActions ra = mvc.perform(get("/api/main/bestproduct"));

        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]",hasSize(list.size())))
                .andExpect(jsonPath("$.[0].productId").value(1L))
                .andExpect(jsonPath("$.[0].title").value("테스트1"))
                .andExpect(jsonPath("$.[0].name").value("네임테스트1"))
                .andExpect(jsonPath("$.[0].price").value(1000))
                .andExpect(jsonPath("$.[0].quantity").value(10))
                .andExpect(jsonPath("$.[0].thumbnail").value("main.pic1"))
                .andExpect(jsonPath("$.[0].volumn").value(100))
                .andDo(print());

        verify(service).bestSell();
    }


    @Test
    void bestSellAll() throws Exception {
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);

        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);

        MainSelVoMaxPaige mainSelVoMaxPaige=new MainSelVoMaxPaige();
        mainSelVoMaxPaige.setMaxPage(10);
        mainSelVoMaxPaige.setList(list);

        given(service.bestSellAll(anyInt(),anyInt())).willReturn(mainSelVoMaxPaige);

        ResultActions ra = mvc.perform(get("/api/main/bestproduct/all").param("page","10").param("row","20"));
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.list.[*]",hasSize(list.size())))
                .andExpect(jsonPath("$.list.[0].productId").value(1L))
                .andExpect(jsonPath("$.list.[0].title").value("테스트1"))
                .andExpect(jsonPath("$.list.[0].name").value("네임테스트1"))
                .andExpect(jsonPath("$.list.[0].price").value(1000))
                .andExpect(jsonPath("$.list.[0].quantity").value(10))
                .andExpect(jsonPath("$.list.[0].thumbnail").value("main.pic1"))
                .andExpect(jsonPath("$.list.[0].volumn").value(100))
                .andExpect(jsonPath("$.maxPage").value(10))
                .andDo(print());

        verify(service).bestSellAll(anyInt(),anyInt());
    }

    @Test
    void postBirthFilter() throws Exception {
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);

        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);

        given(service.birthRecommendFilter(anyInt())).willReturn(list);
        ResultActions ra = mvc.perform(get("/api/main/recommend").param("row","10"));
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]",hasSize(list.size())))
                .andExpect(jsonPath("$.[0].productId").value(1L))
                .andExpect(jsonPath("$.[0].title").value("테스트1"))
                .andExpect(jsonPath("$.[0].name").value("네임테스트1"))
                .andExpect(jsonPath("$.[0].price").value(1000))
                .andExpect(jsonPath("$.[0].quantity").value(10))
                .andExpect(jsonPath("$.[0].thumbnail").value("main.pic1"))
                .andExpect(jsonPath("$.[0].volumn").value(100))
                .andDo(print());

        verify(service).birthRecommendFilter(anyInt());
    }

}