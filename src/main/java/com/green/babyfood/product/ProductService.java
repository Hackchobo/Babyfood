package com.green.babyfood.product;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductReviewEntity;
import com.green.babyfood.product.model.ProductSelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper mapper;
    private final AuthenticationFacade USERPK;

    @Value("${file.dir}")
    private String fileDir;


    public ProductSelDto selProduct(Long productId) {
        log.info("테스트");

        ProductSelDto dto = new ProductSelDto();

        List<String> imgList = mapper.selProductImg(productId);
        List<String> thumbnailList = mapper.selProductThumbnail(productId);
        List<String> baseimg = new ArrayList<>();
        baseimg.add("이미지 없는 상태");

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
        //dto.setCateDetail(mapper.selDataByCategory(productId));
        dto.setImg(imgList);
        if (dto.getImg() == null) {
            dto.setImg(baseimg);
        }

        dto.setThumbnail(thumbnailList);
        if (dto.getThumbnail() == null) {
            dto.setThumbnail(baseimg);
        }

        StringBuilder tempName = new StringBuilder();
        tempName.append("[").append(dto.getStep()).append("단계]").append(dto.getName());
        dto.setName(tempName.toString());



        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime afternoonOne = LocalTime.of(13, 0); // 오후 1시

        // 주말 확인
        boolean isWeekend = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;

        // 배송일 계산
        LocalDate estimatedDeliveryDate = currentDate;
        if (isWeekend || (currentTime.isAfter(afternoonOne) && !isWeekend)) {
            // 주말이거나 현재 시각이 오후 1시 이후이면 배송일을 2일 후로 설정
            estimatedDeliveryDate = currentDate.plusDays(2);
        } else {
            // 현재 시각이 오후 1시 이전이면 배송일을 1일 후로 설정
            estimatedDeliveryDate = currentDate.plusDays(1);
        }

        // 배송일 출력
        log.info("예상 배송일정은 {} 입니다", estimatedDeliveryDate);

        return dto;
    }

    private String createImageUrl(Long productId, String imgName) {
        String path = "http://192.168.0.144:5001/img/webeditor/"+productId+"/"+imgName;
        return path;
    }

    public int postReview(ProductReviewDto dto){
        ProductReviewEntity entity = new ProductReviewEntity();
        entity.setProductId(dto.getProductId());
        entity.setCtnt(dto.getCtnt());
        entity.setIuser(USERPK.getLoginUserPk());
        return mapper.postReview(entity);
    }

    List<ProductReviewDto> selReview(Long productId) {
        return mapper.selReview(productId);
    }
}
