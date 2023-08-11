package com.green.babyfood.user.model;

import lombok.Data;

@Data
public class UserSelEntity extends AdminUserEntity {
    //페이징 처리용
    private int page;
    private int startIdx;
    private int rowLen;
    private int row;
}
