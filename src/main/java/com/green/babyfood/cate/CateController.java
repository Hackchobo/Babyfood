package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cate")
@RequiredArgsConstructor
public class CateController {

    private final CateService service;

    @GetMapping("/cate")
    public List<CateSelDto> getCate(){
        return service.cateSel();
    }
}
