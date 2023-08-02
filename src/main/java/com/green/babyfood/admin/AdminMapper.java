package com.green.babyfood.admin;

import com.green.babyfood.admin.model.AdminProductInsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    int productIns(AdminProductInsDto dto);
}
