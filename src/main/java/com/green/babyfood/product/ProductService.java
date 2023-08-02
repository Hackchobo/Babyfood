package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import com.green.babyfood.product.model.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;

    public ProductSelDto selProduct(int productId){
        return mapper.selProduct(productId);
    }

    public int postReview(ProductReviewDto dto){
        return mapper.postReview(dto);
    }

    List<ReviewEntity> selReview(int productId) {
        return mapper.selReview(productId);
    }
}
