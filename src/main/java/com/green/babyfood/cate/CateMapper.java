package com.green.babyfood.cate;

import com.green.babyfood.cate.model.CateSelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateMapper {
    List<CateSelDto> cateSel();
}
