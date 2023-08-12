package com.green.babyfood.product;

import com.green.babyfood.product.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductSelDto selProduct(Long productId);
    int postReview(ProductReviewEntity entity);
    List<ProductReviewDto> selReview(Long productId);

    List<String> selProductImg(Long productId);
    List<String> selProductThumbnail(Long productId);

    //List<Integer> selDataByCategory(Long productId);

//    List<ProductImgDto> selProductthumbnail(int productId);
}