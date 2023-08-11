//package com.green.babyfood.product;
//
//import com.green.babyfood.config.security.AuthenticationFacade;
//import com.green.babyfood.orderbasket.OrderBasketMapper;
//import com.green.babyfood.orderbasket.OrderBasketService;
//import com.green.babyfood.product.model.ProductReviewDto;
//import com.green.babyfood.product.model.ProductSelDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(SpringExtension.class)
//@Import({ProductService.class, AuthenticationFacade.class})
//public class ProductServiceTest {
//
//    @MockBean
//    private ProductMapper mapper;
//
//    @Autowired
//    private ProductService service;
//
//    @MockBean
//    private AuthenticationFacade USERPK;
//
//
//    @Test
//    void selProduct(){ // 선택한 pk(productId) 상품정보 조회
//
//        Long iuser = USERPK.getLoginUserPk(); // 로그인한 유저의 pk(iuser)
//
//        // Given
//        ProductSelDto selDto = new ProductSelDto();
//        Long productId = 1L;
//
//        ProductReviewDto reviewDto = new ProductReviewDto();
//        reviewDto.setProductId(productId);
//
//        // 모의 객체의 메서드 호출 시 반환할 값을 설정합니다.
//        when(mapper.selProduct(anyLong())).thenReturn(selDto); // selProduct 메소드에 어떤 pk값을 넣어도 dtoSel로 반환
//        when(mapper.postReview(reviewDto)).thenReturn(1); // 리뷰 작성 메소드 호출시 무조건 1 반환
//
//        // When
//        ProductSelDto result = service.selProduct(productId);
//
//    }
//
//}
