package com.green.babyfood.kakao;



import com.green.babyfood.kakao.model.KakaoPaySuccessDto;
import com.green.babyfood.kakao.model.KakaoPaySuccessListDto;
import com.green.babyfood.kakao.model.KakaoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KakaoMapper {

    KakaoVo insProductId(Long productId);
    List<KakaoVo> ins(Long iuser);


    Long insOrderList(KakaoPaySuccessDto dto);
    int insOrderDetail(KakaoPaySuccessDto dto);


    Long insOrderListBasket(KakaoPaySuccessListDto dto);
    int insOrderDetailBasket(KakaoPaySuccessListDto dto);
}
