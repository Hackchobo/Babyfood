package com.green.babyfood.config.security;

import com.green.babyfood.config.security.model.UserEntity;
import com.green.babyfood.config.security.model.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int save(UserEntity p);
    UserEntity getByUid(String email);

    int updUserToken(UserTokenEntity p);
    UserTokenEntity selUserToken(UserTokenEntity p);
}
