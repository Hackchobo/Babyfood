//package com.green.babyfood.product;
//
//import com.green.babyfood.config.security.AuthenticationFacade;
//import com.green.babyfood.product.model.ProductReviewDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
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
//    Long iuser = USERPK.getLoginUserPk(); // 로그인한 유저의 pk(iuser)
//    Long productId = 1L;
//
//    @Test
//    void selReview(){
//        // 선택한 상품의 리뷰 정보 조회 테스트
//
//        // Given
//        List<ProductReviewDto> reviewList = new ArrayList<>();
//        ProductReviewDto reviewList1 = new ProductReviewDto();
//        reviewList1.setProductId(productId);
//        reviewList1.setIuser(iuser);
//        reviewList1.setCtnt("리뷰테스트1");
//
//        ProductReviewDto reviewList2 = new ProductReviewDto();
//        reviewList2.setProductId(productId);
//        reviewList2.setIuser(iuser);
//        reviewList2.setCtnt("리뷰테스트2");
//
//        reviewList.add(reviewList1);
//        reviewList.add(reviewList2);
//
//        // 모의 객체의 메서드 호출 시 반환할 값을 설정
//        when(mapper.selReview(productId)).thenReturn(reviewList);
//        List<ProductReviewDto> testReviewList = service.selReview(productId);
//        assertEquals(testReviewList.get(0).getProductId(), reviewList.get(0).getProductId());
//        assertEquals(testReviewList.get(0).getIuser(), reviewList.get(0).getIuser());
//        assertEquals(testReviewList.get(0).getCtnt(), reviewList.get(0).getCtnt());
//
//
//        verify(mapper).selReview(anyLong());
//
//    }
//
//    @Test
//    void postReview() {
//        //given
//
//    }
//
//    @Test
//    void selProduct() {
//    }
//}
