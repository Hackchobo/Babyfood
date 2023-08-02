package com.green.babyfood.search;

import com.green.babyfood.search.model.SearchtSelVo;
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
    private final SearchService SERVICE;

    @GetMapping
    @Operation(summary = "검색",description = ""+
            "title : 음식제목<br>"+
            "name : 음식이름<br>"+
            "thumbnail: 썸네일<br>"+
            "price : 가격<br>"
    )
    public List<SearchtSelVo> analysisChat(@RequestParam String product,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "30") int row
    ) {
        return SERVICE.selproduct(product,page,row);
    }



    @GetMapping("/filter")
    @Operation(summary = "필터",description = ""+
            " sorter: 0이면 판매량 많은순서 1이면 판매량 적은순서 <br> "+
            "sorter: 2이면 가격 높은 순서 3이면 가격 낮은 순서<br>"+
            "알러지 1~20사이의 값을 보내주세요<br>"+
            "egg는1 milk는2 buckwheat는 3"
    )
    public List<SearchtSelVo> getallergy(@RequestParam String product,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "30") int row,
                                         @RequestParam int sorter,
                                         @RequestParam(required = false)String egg,
                                         @RequestParam(required = false)String milk,
                                         @RequestParam(required = false)String buckwheat,
                                         @RequestParam(required = false)String peanut,
                                         @RequestParam(required = false)String soybean,
                                         @RequestParam(required = false)String wheat,
                                         @RequestParam(required = false)String pine_nut,
                                         @RequestParam(required = false)String walnut,
                                         @RequestParam(required = false)String crab,
                                         @RequestParam(required = false)String shrimp,
                                         @RequestParam(required = false)String squid,
                                         @RequestParam(required = false)String mackerel,
                                         @RequestParam(required = false)String shellfish,
                                         @RequestParam(required = false)String peach,
                                         @RequestParam(required = false)String tomato,
                                         @RequestParam(required = false)String chicken,
                                         @RequestParam(required = false)String pork,
                                         @RequestParam(required = false)String beef,
                                         @RequestParam(required = false)String sulfur_dioxide,
                                         @RequestParam(required = false)String fish
    ){

        return SERVICE.selfilter(product,page,row,sorter, egg,milk,buckwheat,peanut,soybean,wheat,pine_nut,walnut,crab,shrimp,squid,mackerel,shellfish,peach,tomato,chicken,pork,beef,sulfur_dioxide,fish);
    }
}
