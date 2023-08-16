package com.green.babyfood.testPerformance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트 검색페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/test")
public class TestWordController {

    private final TestWordService service;

    // 검색 테스트는 기본 10회 반복으로 세팅되어 있습니다.


    @GetMapping("/komoran")
    @Operation(summary = "코모란 검색 테스트")
    public void getTestKomoran(@RequestParam String word) {
        service.getTestKomoran(word);
    }

    @GetMapping("/okt")
    @Operation(summary = "okt(트위터)검색 테스트")
    public void getTwiiterTest(@RequestParam String word){
        service.getTwiiterTest(word);
    }

}
