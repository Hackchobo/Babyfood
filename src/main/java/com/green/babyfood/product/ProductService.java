package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductImgDto;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import com.green.babyfood.product.model.ReviewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper mapper;

    public ProductSelDto selProduct(int productId) {
        log.info("테스트");

        List<String> imgList = mapper.selProductImg(productId);
        List<String> thumbnailList = mapper.selProductThumbnail(productId);
        ProductSelDto dto=new ProductSelDto();
        dto.setImg(imgList);
        dto.setThumbnail(thumbnailList);
        return dto;
    }

    public int postReview(ProductReviewDto dto){
        return mapper.postReview(dto);
    }

    List<ReviewEntity> selReview(int productId) {
        return mapper.selReview(productId);
    }
}
