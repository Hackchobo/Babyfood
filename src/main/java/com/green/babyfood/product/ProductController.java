package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import com.green.babyfood.product.model.ReviewEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "상품페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "상품 상세보기 페이지",description = ""+
            "productId = 상품 코드")
    ProductSelDto selProduct(Long productId){
        return service.selProduct(productId);
    }

    @PostMapping("/review/ins")
    @Operation(summary = "상품 리뷰 작성", description = ""+
    "iuser = 유저 <br>" +
    "product_id = 상품코드 <br>" +
    "ctnt = 리뷰내용" )
    int postReview(ProductReviewDto dto){
        return service.postReview(dto);
    }

    @GetMapping("/review/{productId}")
    @Operation(summary = "상품 리뷰 보기", description = ""+
    "productId = 상품코드")
    List<ProductReviewDto> selReview(@RequestParam Long productId){
        return service.selReview(productId);
    }

}
