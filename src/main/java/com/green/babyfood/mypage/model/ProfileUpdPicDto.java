package com.green.babyfood.mypage.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileUpdPicDto {
    private String img;
    private Long iuser;
}
