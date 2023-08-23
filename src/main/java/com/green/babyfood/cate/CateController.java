package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelLevelDto;
import com.green.babyfood.cate.model.CateSelLevelVo;
import com.green.babyfood.cate.model.CateView;
import io.swagger.v3.oas.annotations.Operation;

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
    @Operation(summary = "카테고리 클릭 시 해당 품목만 보여주는 메소드",description = ""+
    "cateId: 1차 카테고리"+
    "cateDetailId : 2차 카테고리"+
    "page : 페이지"+
    "row : 보여줄 상품의 개수"  +

    "productId : 해당 상품의 고유 번호<br>"+
    "thumbnail : 해당 상품의 썸네일<br>"+
    "title : 해당 상품의 제목<br>"+
    "name : 해당 상품의 이름<br>"+
    "price : 해당 상품의 가격<br>"+
    "quantity : 해당 상품의 재고량<br>"+
    "volumn : 해당 상품의 판매량")
    public CateSelLevelVo getLevel(CateSelLevelDto dto){
        return service.cateSelLevel(dto);
    }


    @GetMapping("/all")
    @Operation(summary = "카테고리 목록")
    public List getAll(){
        return service.selCateList();
    }
}
