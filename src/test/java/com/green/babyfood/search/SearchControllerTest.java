package com.green.babyfood.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.model.MyUserDetails;
import com.green.babyfood.mypage.MypageService;
import com.green.babyfood.search.model.SearchSelRes;
import com.green.babyfood.search.model.SearchSelVo;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SearchService service;

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
    void getproduct() throws Exception {
//
//        String productname = "밥";
//        int page = 1;
//        int row = 10;
//
//        SearchSelRes res = new SearchSelRes();
//        List<SearchSelVo> dto = new ArrayList<>();
//        SearchSelVo vo = new SearchSelVo();
//        SearchSelVo vo2 = new SearchSelVo();
//        vo.setProductid(1);
//        vo.setThumbnail("rice.png");
//        vo.setName("곤드레밥");
//        vo.setPrice(1000);
//
//        vo2.setProductid(12);
//        vo2.setThumbnail("rice.png");
//        vo2.setName("갈비밥");
//        vo2.setPrice(1000);
//
//        res.setMaxpage(1);
//        res.setCount(3);
//        res.setDto(dto);
//
//        given(service.selproduct(any(),anyInt(),anyInt())).willReturn(res);
//
//        ResultActions ra = mvc.perform(get("/api/search")
//                .param("밥", productname)
//                .param("1", String.valueOf(page))
//                .param("10", String.valueOf(row))
//                .contentType(MediaType.APPLICATION_JSON));
//        ra.andExpect(status().isOk())
//                .andDo(print());
//        verify(service).selproduct(any(),anyInt(),anyInt());
//


    }

    @Test
    void filterAllergy() {
    }
}