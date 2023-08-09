package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserEntity1> selUser();
    int updUser(UserUpdDto1 dto);
    int updPicUser(CreatePicDto dto);
    int updPointUser(UserPointDto dto);
    int delUser(UserDelDto dto);
    int deltoken(UserDelDto dto);

}
