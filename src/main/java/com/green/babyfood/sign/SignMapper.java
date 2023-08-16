package com.green.babyfood.sign;

import com.green.babyfood.sign.model.SignIdDto;
import com.green.babyfood.sign.model.SignPwDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignMapper {
    SignPwDto findPassword(String mail, String mobileNb);

    void updPassword(Long iuser, String pw);
    String emailCheck(String email);
    String SelNickNm(String nickNm);

    SignIdDto findUserId(String mobileNb);
}
