package com.green.babyfood.product;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductReviewEntity;
import com.green.babyfood.product.model.ProductSelDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@Import({ProductService.class, AuthenticationFacade.class})
public class ProductServiceTest {

  @MockBean
  private ProductMapper mapper;

  @Autowired
  private ProductService service;

  @MockBean
  private AuthenticationFacade USERPK;

  Long iuser = 1L; // 로그인한 유저의 pk(iuser)
  Long productId = 1L;

    @Test
  void selReview(){
      // 선택한 상품의 리뷰 정보 조회 테스트

      // Given
      List<ProductReviewDto> reviewList = new ArrayList<>();
      ProductReviewDto reviewList1 = new ProductReviewDto();
      reviewList1.setProductId(productId);
      //reviewList1.setIuser(iuser);
      reviewList1.setCtnt("리뷰테스트1");

      ProductReviewDto reviewList2 = new ProductReviewDto();
      reviewList2.setProductId(productId);
      //reviewList2.setIuser(iuser);
      reviewList2.setCtnt("리뷰테스트2");

      reviewList.add(reviewList1);
      reviewList.add(reviewList2);

      // 모의 객체의 메서드 호출 시 반환할 값을 설정
      when(mapper.selReview(productId)).thenReturn(reviewList);
      List<ProductReviewDto> testReviewList = service.selReview(productId);
      assertEquals(testReviewList.get(0).getProductId(), reviewList.get(0).getProductId());
      //assertEquals(testReviewList.get(0).getIuser(), reviewList.get(0).getIuser());
      assertEquals(testReviewList.get(0).getCtnt(), reviewList.get(0).getCtnt());


      verify(mapper).selReview(anyLong());

  }

    @Test
    public void PostReview() {
        // Arrange
        ProductReviewDto dto = new ProductReviewDto();
        dto.setProductId(1L);
        dto.setCtnt("리뷰작성테스트");

        when(mapper.postReview(any(ProductReviewEntity.class))).thenReturn(1);

        // Act
        int result = service.postReview(dto);

        // Assert
        assertEquals(1, result);
        verify(mapper, times(1)).postReview(any(ProductReviewEntity.class));
    }

   @Test
   public void testSelProduct() {
       // Arrange
       Long productId = 1L;
       ProductSelDto expectedDto = new ProductSelDto();
       expectedDto.setName("Test Product");
       expectedDto.setStep(2);
       expectedDto.setImg(Collections.singletonList("test-image-url"));
       expectedDto.setThumbnail(Collections.singletonList("test-thumbnail-url"));

       when(mapper.selProduct(productId)).thenReturn(expectedDto);
       when(mapper.selProductImg(productId)).thenReturn(Collections.singletonList("test-image-name"));
       when(mapper.selProductThumbnail(productId)).thenReturn(Collections.singletonList("test-thumbnail-name"));

       // Act
       ProductSelDto resultDto = service.selProduct(productId);

       // Assert
       assertEquals(expectedDto.getName(), resultDto.getName());
       assertEquals(expectedDto.getStep(), resultDto.getStep());
       assertEquals(expectedDto.getImg(), resultDto.getImg());
       assertEquals(expectedDto.getThumbnail(), resultDto.getThumbnail());

       // Verify
       verify(mapper, times(1)).selProduct(productId);
       verify(mapper, times(1)).selProductImg(productId);
       //verify(mapper, times(1)).selProductThumbnail(productId);
   }
}
