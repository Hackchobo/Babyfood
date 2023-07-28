package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    List<SelMypageOrderListDto> mypageOrderlist(int iuser);
    SelprofileDto profile(int iuser);
    int Updprofile(UpdProfileDto dto);
    SelNickNmDto SelNickNm(String nickNm);
    int delUser(int iuser);
    List<SelOrderlistDto> Orderlist(int iuser);
    List<SelOrderlistDto>selOneMonths(SelOrderlistMonthsDto dto);


}
