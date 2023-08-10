package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface MypageMapper {
    ProfileSelDto profile(Long iuser);
    int Updprofile(ProfileUpdDto dto);
    String SelNickNm(String nickNm);
    int delUser(Long iuser);
    List<OrderlistCountSelDto> orderlist(OrderlistMonthsSelDto dto);
    List<OrderlistDetailSelDto> orderlistDetail(Long orderId);
    OrderlistUserDto selUser(Long orderId);
    int delorder(Long orderId);


    int patchProfile(MultipartFile img, Long iuser);
}
