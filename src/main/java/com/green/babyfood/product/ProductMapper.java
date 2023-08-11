package com.green.babyfood.product;

import com.green.babyfood.product.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductSelDto selProduct(Long productId);
    int postReview(ProductReviewDto dto);
    List<ReviewEntity> selReview(int productId);

    List<String> selProductImg(Long productId);
    List<String> selProductThumbnail(Long productId);

//    List<ProductImgDto> selProductthumbnail(int productId);
}