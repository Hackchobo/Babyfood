package com.green.babyfood.cate.model;

import lombok.Data;

import java.util.List;

@Data
public class CateSelLevelDto {

    private Long cateId;
    private Long cateDetailId;
    private int page;
    private int row;
    private List<String> allergy;
}
