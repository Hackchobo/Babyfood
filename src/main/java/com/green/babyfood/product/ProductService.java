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

    public ProductSelDto selProduct(int productId){

        List<ProductImgDto> productImgDto = mapper.selProductImg(productId);
        List<ProductImgDto> productthumbnailDto = mapper.selProductthumbnail(productId);
        log.info("테스트");
        ProductSelDto dto = mapper.selProduct(productId);
        ProductSelDto build = ProductSelDto.builder()
                .allergy(dto.getAllergy()).price(dto.getPrice()).step(dto.getStep())
                .name(dto.getName()).title(dto.getTitle())
                .img(productImgDto).thumbnail(productthumbnailDto)
                .quantity(dto.getQuantity()).build();
        return build;
    }

    public int postReview(ProductReviewDto dto){
        return mapper.postReview(dto);
    }

    List<ReviewEntity> selReview(int productId) {
        return mapper.selReview(productId);
    }
}
