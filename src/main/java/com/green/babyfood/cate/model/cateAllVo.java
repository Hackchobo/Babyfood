package com.green.babyfood.cate.model;

import lombok.Data;

import java.util.List;

@Data
public class cateAllVo {

    private Long cateId;
    private String cateName;
    private List<cateDetailVo> list;
}
