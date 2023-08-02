package com.green.babyfood.search;

import com.green.babyfood.search.model.SearchSelDto;
import com.green.babyfood.search.model.SearchtSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<SearchtSelVo> selproduct(SearchSelDto dto);
    List<SearchtSelVo> selfilter(SearchSelDto dto);
}
