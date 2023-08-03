package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    List<MypageSelOrderListDto> mypageOrderlist(int iuser);
    ProfileSelDto profile(int iuser);
    int Updprofile(ProfileUpdDto dto);
    NickNmDto SelNickNm(String nickNm);
    int delUser(int iuser);
    List<OrderlistSelDto> Orderlist(OrderlistMonthsSelDto dto);
    List<OrderlistDetailSelDto> OrderlistDetail(OrderlistDetailSelVo vo);


}
