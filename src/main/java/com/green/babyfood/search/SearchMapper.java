package com.green.babyfood.search;

import com.green.babyfood.search.model.SearchSelDto;
import com.green.babyfood.search.model.SearchSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<SearchSelVo> selproduct(SearchSelDto dto);
    List<SearchSelVo> selfilter(SearchSelDto dto);
    int maxpage(String strallergy);
}
