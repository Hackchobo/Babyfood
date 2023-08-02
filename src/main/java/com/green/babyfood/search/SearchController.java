package com.green.babyfood.search;

import com.green.babyfood.search.model.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "검색페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Search")
public class SearchController {
    private final SearchService service;

    @GetMapping
    @Operation(summary = "검색",description = ""+
            "title : 음식제목<br>"+
            "name : 음식이름<br>"+
            "thumbnail: 썸네일<br>"+
            "price : 가격")
    public List<ProductDto> analysisChat(@RequestParam String msg) {
        return service.selproduct(msg);
    }


}
