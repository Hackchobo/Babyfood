package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insUser(UserInsDto dto);
    int insAdmin(AdminInsDto dto);
    List<UserEntity> selUser();
    int updUser(UserUpdDto dto);
    int updPicUser(CreatePicDto dto);
    int updPointUser(UserPointDto dto);
    int delUser(UserDelDto dto);
}
