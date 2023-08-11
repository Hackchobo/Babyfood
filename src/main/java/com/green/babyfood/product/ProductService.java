package com.green.babyfood.product;

import com.green.babyfood.product.model.ProductImgDto;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductSelDto;
import com.green.babyfood.product.model.ReviewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.green.babyfood.util.FileUtils.getAbsolutePath;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper mapper;

    @Value("${file.dir}")
    private String fileDir;

    public ProductSelDto selProduct(Long productId) {
        log.info("테스트");

        List<String> imgList = mapper.selProductImg(productId);
        List<String> thumbnailList = mapper.selProductThumbnail(productId);

        List<String> imgUrls = new ArrayList<>();
        for (String imgName : imgList) {
            if(imgName != null) {
                String imgUrl = createImageUrl(productId, imgName); // URL 생성
                imgUrls.add(imgUrl);
            }
        }
        imgList = imgUrls;

        List<String> thumbnailUrls = new ArrayList<>();
        for (String thumbnailName : thumbnailList) {
            if(thumbnailName != null) {
                String thumbnailUrl = createImageUrl(productId, thumbnailName); //URL 생성
                thumbnailUrls.add(thumbnailUrl);
            }
        }
        thumbnailList = thumbnailUrls;

        ProductSelDto dto = mapper.selProduct(productId);
        dto.setImg(imgList);
        dto.setThumbnail(thumbnailList);


//        log.info("예상 배송일정 확인 시작");




        return dto;
    }

    private String createImageUrl(Long productId, String imgName) {
        String path = "http://192.168.0.144:5001/img/webeditor/"+productId+"/"+imgName;
        return path;
    }

    public int postReview(ProductReviewDto dto){
        return mapper.postReview(dto);
    }

    List<ReviewEntity> selReview(int productId) {
        return mapper.selReview(productId);
    }
}
