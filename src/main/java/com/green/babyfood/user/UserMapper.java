package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<AdminUserEntity> selUser(UserSelEntity entity);
    int updUser(AdminUserUpdDto dto);
    int updPicUser(CreatePicDto dto);
    int updPointUser(UserPointDto dto);
    int delUser(UserDelDto dto);
    int deltoken(UserDelDto dto);

}
