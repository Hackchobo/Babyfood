package com.green.babyfood.admin;

import com.green.babyfood.admin.model.AdminProductInsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper mapper;

    int productIns(AdminProductInsDto dto){
        return mapper.productIns(dto);
    }
}
