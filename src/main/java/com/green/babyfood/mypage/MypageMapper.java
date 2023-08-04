package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    ProfileSelDto profile(int iuser);
    int Updprofile(ProfileUpdDto dto);
    MypageNickNmDto SelNickNm(String nickNm);
    int delUser(int iuser);
    List<OrderlistCountSelDto> Orderlist(OrderlistMonthsSelDto dto);
    List<OrderlistDetailSelDto> OrderlistDetail(int orderId);
    OrderlistUserDto selUser(int orderId);



}
