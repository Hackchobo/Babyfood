package com.green.babyfood.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.SecurityConfiguration;
import com.green.babyfood.orderbasket.OrderBasketController;
import com.green.babyfood.orderbasket.OrderBasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderBasketController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
@Slf4j
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductMapper productMapper;
    @MockBean
    private ProductService productService;

    @MockBean
    private RedisService redisService;



}
