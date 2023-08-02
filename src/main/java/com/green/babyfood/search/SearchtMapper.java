package com.green.babyfood.search;

import com.green.babyfood.search.model.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchtMapper {
    List<ProductDto> selproduct(String msg);
}
