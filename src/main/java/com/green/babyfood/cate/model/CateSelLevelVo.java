package com.green.babyfood.cate.model;

import lombok.Data;

import java.util.List;

@Data
public class CateSelLevelVo {

    private int maxPaige;
    private List<CateSelListVo> list;
}
