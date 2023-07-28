package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelLevelDto;
import com.green.babyfood.cate.model.CateSelLevelVo;
import com.green.babyfood.cate.model.CateView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cate")
@RequiredArgsConstructor
public class CateController {

    private final CateService service;


    @GetMapping("/list")
    public CateSelLevelVo getLevel(CateSelLevelDto dto){
        return service.cateSelLevel(dto);
    }




    @GetMapping("/all")
    private List getAll(){
        return service.selCateList();
    }
}
