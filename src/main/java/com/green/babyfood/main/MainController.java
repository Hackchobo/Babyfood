package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Tag(name = "메인페이지")
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService service;

    @GetMapping
    @Operation(summary = "기본으로 보여줄 상품", description = "" +
            "paige : 페이지번호<br>" +
            "row : 한페이지에 보일 상품의 갯수")
    public MainSelVoMaxPaige selPaging(@RequestParam @Min(value = 1) int page, @RequestParam int row) {
        return service.mainSelView(page, row);
    }


//    @GetMapping("/random")
//    @Operation(summary = "랜덤으로 상품추천", description = "" +
//            "productId : 상품의 고유번호<br>" +
//            "thumbnail : 상품의 썸네일<br>" +
//            "title : 상품의 제목<br>" +
//            "name : 상품의 이름<br>" +
//            "price : 상품의 가격<br>" +
//            "quantity : 상품의 재고<br>" +
//            "volumn : 판매량")
//    public List<MainSelVo> random(@RequestParam(required = false) String egg, @RequestParam(required = false) String milk,
//                                  @RequestParam(required = false) String buckwheat, @RequestParam(required = false) String peanut,
//                                  @RequestParam(required = false) String soybean, @RequestParam(required = false) String wheat,
//                                  @RequestParam(required = false) String pine_nut, @RequestParam(required = false) String walnut,
//                                  @RequestParam(required = false) String crab, @RequestParam(required = false) String shrimp,
//                                  @RequestParam(required = false) String squid, @RequestParam(required = false) String mackerel,
//                                  @RequestParam(required = false) String shellfish, @RequestParam(required = false) String peach,
//                                  @RequestParam(required = false) String tomato, @RequestParam(required = false) String chicken,
//                                  @RequestParam(required = false) String pork, @RequestParam(required = false) String beef,
//                                  @RequestParam(required = false) String sulfur_dioxide, @RequestParam(required = false) String fish) {
//        return service.random(egg,milk,buckwheat,peanut,soybean,wheat,pine_nut,walnut,crab,shrimp,squid,
//                mackerel,shellfish,peach,tomato,chicken,pork,beef,sulfur_dioxide,fish);
//    }

    @GetMapping("/bestproduct")
    @Operation(summary = "제일 많이 팔린 상품", description = "" +
            "productId : 상품의 고유번호<br>" +
            "thumbnail : 상품의 썸네일<br>" +
            "title : 상품의 제목<br>" +
            "name : 상품의 이름<br>" +
            "price : 상품의 가격<br>" +
            "quantity : 상품의 재고<br>" +
            "volumn : 판매량")
    public List<MainSelVo> bestSell() {
        return service.bestSell();
    }

    @GetMapping("/bestproduct/all")
    @Operation(summary = "제일 많이 팔린 상품 더보기",description = "" +
            "productId : 상품의 고유번호<br>" +
            "thumbnail : 상품의 썸네일<br>" +
            "title : 상품의 제목<br>" +
            "name : 상품의 이름<br>" +
            "price : 상품의 가격<br>" +
            "quantity : 상품의 재고<br>" +
            "volumn : 판매량")
    public MainSelVoMaxPaige bestSellAll(@RequestParam int page,@RequestParam int row) {
        return service.bestSellAll(page,row);
    }



//   @GetMapping("/recommend")
//   @Operation(summary = "회원 자녀의 개월에따라 상품추천",description = ""+
//           "productId : 상품의 고유번호<br>"+
//           "thumbnail : 상품의 썸네일<br>"+
//           "title : 상품의 제목<br>"+
//           "name : 상품의 이름<br>"+
//           "price : 상품의 가격<br>"+
//           "quantity : 상품의 재고<br>"+
//           "volumn : 판매량<br>"+
//           "maxPaige : 최대페이지수<br>"+
//   "4개월 이하는 이유식이 먹을 나이가 아닙니다")
//   public MainSelVoMaxPaige postBirth(Long iuser,int page,int row){
//      return service.birthRecommend(iuser,page,row);
//  }

    @GetMapping("/recommend")
    @Operation(summary = "회원 자녀의 개월에따라 상품추천", description = "" +
            "productId : 상품의 고유번호<br>" +
            "thumbnail : 상품의 썸네일<br>" +
            "title : 상품의 제목<br>" +
            "name : 상품의 이름<br>" +
            "price : 상품의 가격<br>" +
            "quantity : 상품의 재고<br>" +
            "volumn : 판매량<br>" +
            "maxPaige : 최대페이지수<br>" +
            "4개월 이하는 이유식이 먹을 나이가 아닙니다")
    public List<MainSelVo> postBirthFilter(int row) {
        return service.birthRecommendFilter(row);
    }
}
