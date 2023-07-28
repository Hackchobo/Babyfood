package com.green.babyfood.research;

import com.green.babyfood.research.model.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "검색")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/research")
public class ResearchController {
    private final ResearchService service;

    @GetMapping
    @Operation(summary = "검색",description = ""+
            "title : 음식제목"+
            "name : 음식이름<br>"+
            "price : 가격"+
            "quantity : 수량"+
            "description: 메모"+
            "salevolumn : 판매수량"+
            "allergy : 알레르기 종류")
    public List<ProductDto> analysisChat(@RequestParam String msg) {
        return service.selproduct(msg);
    }


}
