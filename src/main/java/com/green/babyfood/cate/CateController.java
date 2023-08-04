package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelLevelDto;
import com.green.babyfood.cate.model.CateSelLevelVo;
import com.green.babyfood.cate.model.CateView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "카테고리")
@RestController
@RequestMapping("/api/cate")
@RequiredArgsConstructor
public class CateController {

    private final CateService service;


    @GetMapping("/list")
    @Operation(summary = "카테고리 클릭시 해당품목만 보여주는 메소드",description = ""+
    "productId : 해당상품의 고유번호<br>"+
    "thumbnail : 해당상품의 썸네일<br>"+
    "title : 해당상품의 제목<br>"+
    "name : 해당상품의 이름<br>"+
    "price : 해당상품의 가격<br>"+
    "quantity : 해당상품의 재고량<br>"+
    "volumn : 해당상품의 판매량")
    public CateSelLevelVo getLevel(@RequestBody CateSelLevelDto dto,
                                   @RequestParam(required = false) String egg,
                                   @RequestParam(required = false) String milk,
                                   @RequestParam(required = false) String buckwheat,
                                   @RequestParam(required = false) String peanut,
                                   @RequestParam(required = false) String soybean,
                                   @RequestParam(required = false) String wheat,
                                   @RequestParam(required = false) String pine_nut,
                                   @RequestParam(required = false) String walnut,
                                   @RequestParam(required = false) String crab,
                                   @RequestParam(required = false) String shrimp,
                                   @RequestParam(required = false) String squid,
                                   @RequestParam(required = false) String mackerel,
                                   @RequestParam(required = false) String shellfish,
                                   @RequestParam(required = false) String peach,
                                   @RequestParam(required = false) String tomato,
                                   @RequestParam(required = false) String chicken,
                                   @RequestParam(required = false) String pork,
                                   @RequestParam(required = false) String beef,
                                   @RequestParam(required = false) String sulfur_dioxide,
                                   @RequestParam(required = false) String fish){
        return service.cateSelLevel(dto,egg, milk, buckwheat, peanut, soybean, wheat, pine_nut, walnut, crab,
                shrimp, squid, mackerel, shellfish, peach, tomato, chicken, pork, beef, sulfur_dioxide, fish);
    }


    @GetMapping("/all")
    @Operation(summary = "카테고리 목록")
    public List getAll(){
        return service.selCateList();
    }
}
