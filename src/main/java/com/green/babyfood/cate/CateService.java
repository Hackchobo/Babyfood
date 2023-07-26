package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CateService {

    private final CateMapper mapper;

    public List<CateSelDto> cateSel(){
        return mapper.cateSel();
    }
}
