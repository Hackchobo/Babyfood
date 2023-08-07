package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import com.green.babyfood.product.model.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductSelDto selProduct(int productId);
    int postReview(ProductReviewDto dto);
    List<ReviewEntity> selReview(int productId);

    void selProductImg(int productId);
    void selProductthumbnailint(int productId);
}
