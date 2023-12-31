package com.green.babyfood.product;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.product.model.ProductBuyDto;
import com.green.babyfood.product.model.ProductReviewDto;
import com.green.babyfood.product.model.ProductReviewEntity;
import com.green.babyfood.product.model.ProductSelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

        ProductSelDto dto = mapper.selProduct(productId);

        List<String> imgList = mapper.selProductImg(productId);
        List<String> thumbnailList = mapper.selProductThumbnail(productId);
        log.info("테스트 : {}", thumbnailList);
        List<String> baseimg = new ArrayList<>();
        baseimg.add("이미지 없는 상태");

        List<String> imgUrls = new ArrayList<>();
        for (String imgName : imgList) {
            if(imgName != null) {
                String address = "webeditor";
                String imgUrl = createImageUrl(productId, imgName, address); // URL 생성
                imgUrls.add(imgUrl);
            }
        }
        imgList = imgUrls;

        List<String> thumbnailUrls = new ArrayList<>();
        for (String thumbnailName : thumbnailList) {
            String address = "product";
            if(thumbnailName != null) {
                String thumbnailUrl = createImageUrl(productId, thumbnailName, address); //URL 생성
                thumbnailUrls.add(thumbnailUrl);
            }
        }
        thumbnailList = thumbnailUrls;
        log.info("테스트 : {}", thumbnailList);
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
        boolean isSaturday = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY;
        boolean isSunday = currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;

        // 기본 배송일은 오늘 날짜로부터 1일 후
        LocalDate estimatedDeliveryDate = currentDate.plusDays(1);
        // 배송일 계산
        if (isSaturday) {
            // 토요일인 경우 배송일 + 2
            estimatedDeliveryDate = currentDate.plusDays(2);
        } else if (isSunday || (currentTime.isAfter(afternoonOne) && !isSaturday)) {
            // 일요일이거나 현재 시각이 오후 1시 이후인 경우 배송일 + 1
            estimatedDeliveryDate = currentDate.plusDays(1);
        } else {
            // 나머지 경우에는 배송일을 그대로 설정
            estimatedDeliveryDate = currentDate;
        }

        // 배송일 출력
        log.info("예상 배송일정은 {} 입니다", estimatedDeliveryDate);

        return dto;
    }

    private String createImageUrl(Long productId, String imgName, String address) {
        String path = "http://192.168.0.144:5001/img/"+ address+ "/" + productId+ "/" +imgName;
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

    public ProductBuyDto selBuyProduct(Long productId) {
        return mapper.selBuyProduct(productId);
    }
}
