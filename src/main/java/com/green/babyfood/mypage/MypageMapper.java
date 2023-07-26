package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.SelNickNmDto;
import com.green.babyfood.mypage.model.SelOrderlistDto;
import com.green.babyfood.mypage.model.SelprofileDto;
import com.green.babyfood.mypage.model.UpdProfileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    List<SelOrderlistDto> mypageOrderlist(int iuser);
    SelprofileDto profile(int iuser);
    int Updprofile(UpdProfileDto dto);
    SelNickNmDto SelNickNm(String nickNm);
    int delUser(int iuser);


}
