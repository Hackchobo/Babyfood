package com.green.babyfood.config.security;

import com.green.babyfood.config.security.model.UserEntity;
import com.green.babyfood.config.security.model.UserTokenEntity;
import com.green.babyfood.sign.model.SignEntity;
import com.green.babyfood.sign.model.UserUpdDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int save(SignEntity p);
    UserEntity getByUid(String email);
    int updSecretKey(UserUpdDto dto);

    int updUserToken(UserTokenEntity p);
    UserTokenEntity selUserToken(UserTokenEntity p);
}
