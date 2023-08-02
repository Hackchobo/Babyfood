package com.green.babyfood.admin.model;

import lombok.Data;

@Data
public class AdminProductDto extends AdminProductEntity{
    private int page;
    private int startIdx;
    private int rowLen;
    private int row;

    // 페이징 처리용
}
