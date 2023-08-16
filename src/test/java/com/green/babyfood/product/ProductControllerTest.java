package com.green.babyfood.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.config.security.SecurityConfiguration;
import com.green.babyfood.orderbasket.OrderBasketController;
import com.green.babyfood.orderbasket.OrderBasketService;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
@Slf4j
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
//    @Autowired
//    private ProductMapper productMapper;
    @MockBean
    private ProductService productService;

    @MockBean
    private RedisService redisService;

    @Test
    public void selProduct() {

        Long productId = 1L;
        ProductSelDto expectedDto = new ProductSelDto();
        //테스트를 위해 값 주입
        expectedDto.setName("Test Product");
        expectedDto.setStep(2);
        expectedDto.setImg(Collections.singletonList("test/img/url"));
        expectedDto.setThumbnail(Collections.singletonList("test/thumbnail/url"));

        when(productService.selProduct(productId)).thenReturn(expectedDto);
        ProductSelDto resultDto = productService.selProduct(productId);


        assertEquals(expectedDto.getName(), resultDto.getName());
        assertEquals(expectedDto.getStep(), resultDto.getStep());
        assertEquals(expectedDto.getImg(), resultDto.getImg());
        assertEquals(expectedDto.getThumbnail(), resultDto.getThumbnail());


        verify(productService).selProduct(anyLong());
    }

    @Test
    void postReview(){
        //     private Long productId;
        //    private String ctnt;

        ProductReviewDto expectedDto1 = new ProductReviewDto();
        expectedDto1.setProductId(1L);
        expectedDto1.setCtnt("test");

        ProductReviewDto expectedDto2 = new ProductReviewDto();
        expectedDto2.setProductId(2L);
        expectedDto2.setCtnt("test2");

        when(productService.postReview(expectedDto1)).thenReturn(1);
        when(productService.postReview(expectedDto2)).thenReturn(1);

        assertEquals(expectedDto1.getProductId(), 1);
        assertEquals(expectedDto1.getCtnt(),"test");
        assertEquals(expectedDto2.getProductId(), 2);
        assertEquals(expectedDto2.getCtnt(),"test2");

    }

}
