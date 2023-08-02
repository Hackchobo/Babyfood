package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {

    List<MainSelVo> mainSelView(int startIdx,int row);
    int maxPaige();

}
