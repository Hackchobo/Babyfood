package com.green.babyfood.cate;

import com.green.babyfood.cate.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateMapper {

    int cateSelLevelmaxPage(CateSelLevelDto2 dto);
    List<CateSelListVo> cateSelLevel(CateSelLevelDto2 dto);


    List<CateVo> selcate();
    List<CateDetailVo> selCateList(Long cateId);
}
