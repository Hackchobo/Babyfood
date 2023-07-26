package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@Tag(name = "메인페이지")
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService service;

    @GetMapping
    @Operation(summary = "메인페이지 페이징",description = ""+
    "paige : 페이지번호<br>"+
    "row : 한페이지에 보일 상품의 갯수")
    public MainSelVoMaxPaige selPaging(@RequestParam @Min(value = 1) int paige, @RequestParam int row){
        return service.mainSelView(paige,row);
    }
}
