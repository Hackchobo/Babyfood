package com.green.babyfood.search;

import com.green.babyfood.search.model.SearchRes;
import com.green.babyfood.search.model.SearchSelRes;
import com.green.babyfood.search.model.SearchSelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "검색페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService SERVICE;

    @GetMapping
    @Operation(summary = "검색",description = ""+
            "title : 음식 제목<br>"+
            "name : 음식 이름<br>"+
            "thumbnail: 썸네일<br>"+
            "price : 가격<br>"
    )
    public SearchSelRes getproduct(@RequestParam String product,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "30") int row
    ) {
        return SERVICE.selproduct(product,page,row);
    }

    @GetMapping("/filter")
    @Operation(summary = "필터",description = ""+
            " sorter: 0이면 판매량 많은 순서 1이면 판매량 적은 순서 <br> "+
            "sorter: 2이면 가격 높은 순서 3이면 가격 낮은 순서<br>")
    public SearchSelRes filterAllergy(@RequestParam String product,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int row,
                                      @RequestParam int sorter,
                                      @RequestParam List<String>filter){
        SearchSelRes selfilter = SERVICE.selfilter(product,page,row,sorter,filter);
        return selfilter;
    }

    @PostMapping("/filter")
    @Operation(summary = "필터",description = ""+
            " sorter: 0이면 판매량 많은 순서 1이면 판매량 적은 순서 <br> "+
            "sorter: 2이면 가격 높은 순서 3이면 가격 낮은 순서<br>")
    public SearchSelRes filterpostAllergy(@RequestBody SearchRes res){
        SearchSelRes selfilter = SERVICE.selpostfilter(res);
        return selfilter;
    }
}
