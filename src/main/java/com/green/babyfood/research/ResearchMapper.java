package com.green.babyfood.research;

import com.green.babyfood.research.model.ProductDto;
import kr.co.shineware.nlp.komoran.model.Token;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResearchMapper {
    List<ProductDto> selproduct(String msg);
}
