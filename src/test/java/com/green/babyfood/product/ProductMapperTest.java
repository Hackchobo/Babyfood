package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductReviewDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductMapperTest {
    @Autowired
    private ProductMapper mapper;

    @Test
    void postReview(){
        ProductReviewDto dto = new ProductReviewDto(); // 테스트를 위해 생성
        dto.setIuser(1L); //
        dto.setProductId(1L);
        dto.setCtnt("테스트용 리뷰");

        // When
        int result = mapper.postReview(dto);

        // Then
        assertEquals(1, result);
    }
}
