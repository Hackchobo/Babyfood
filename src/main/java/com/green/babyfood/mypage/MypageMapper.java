package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import com.green.babyfood.user.model.UserDelDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Mapper
public interface MypageMapper {
    ProfileSelDto profile(OrderIuserDto dto);
    int Updprofile(ProfileEntity entity);
    String SelNickNm(String nickNm);
    String selmyNickNm(Long iuser);

    int delUser(OrderIuserDto dto);
    List<OrderlistCountSelDto> orderlist(OrderlistMonthsSelDto dto);
    List<OrderlistCountSelDto> orderlistDetail(Long orderId);
    OrderlistUserDto selUser(Long orderId);
    int delorder(Long orderId);
    int count(Long orderId);
    int patchProfile(ProfileUpdPicDto dto);

    String selpw(Long iuser);
}
