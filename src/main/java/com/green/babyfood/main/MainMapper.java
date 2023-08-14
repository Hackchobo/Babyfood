package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {

    List<MainSelVo> mainSelView(int startIdx,int row);
    int maxPage();

    List<MainSelVo> random();
    List<MainSelVo> bestSell();
    List<MainSelVo> bestSellAll(int startIdx,int row);
    int bestSellAllMaxPage();

    int birth(Long iuser);
//    List<MainSelVo> birthRecommend(int cate,int startIdx,int row);
//    int birthMaxPage(int cate);

    List<MainSelVo> birthRecommendFilter(int cate,int row);
//    int birthRecommendFilterMaxPaige(int cate,String strallergy);
}
