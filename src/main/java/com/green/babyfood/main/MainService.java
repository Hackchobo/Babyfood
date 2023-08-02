package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService  {

    private final MainMapper mapper;


    public MainSelVoMaxPaige mainSelView(int paige, int row){
       int startIdx=(paige-1)*row;
        List<MainSelVo> mainSelVos = mapper.mainSelView(startIdx, row);

        int maxPaige1 = mapper.maxPaige();
        int maxPaige2= (int) Math.ceil((double)maxPaige1/row);
        MainSelVoMaxPaige mainSelVoMaxPaige=new MainSelVoMaxPaige();
        mainSelVoMaxPaige.setMaxPaige(maxPaige2);
        mainSelVoMaxPaige.setList(mainSelVos);

        return mainSelVoMaxPaige;
    }

    public List<MainSelVo> random(){
        return mapper.random();
    }

    public List<MainSelVo> bestSell(){
        return mapper.bestSell();
    }

    public List<MainSelVo> birthRecommend(Long iuser){
       int month=mapper.birth(iuser);
       int cate=0;
       if(month<=4){
           throw new RuntimeException("이유식 먹을수 있는 나이가 아닙니다");
       }
       if(month>4 && month<=6){
           cate=1;
       } else if (month>6 && month<=10) {
           cate=2;
       } else if (month>10 && month<=13) {
           cate=3;
       }else if(month>13){
           cate=4;
       }
       return mapper.birthRecommend(cate);
    }
}
