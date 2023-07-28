package com.green.babyfood.cate;

import com.green.babyfood.cate.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateMapper {

    int cateSelLevelmaxPaige(Long cateId,Long cateDetailId);
    List<CateSelListVo> cateSelLevel(CateSelLevelDto dto);
    List<CateVo> cateAll();
    List<cateDetailVo> cateDetailAll();


    CateVo selcate();
    List<CateView> selCateList();
}
