package com.green.babyfood.main.model;

import lombok.Data;

import java.util.List;

@Data
public class MainSelVoMaxPaige {
    private int maxPage;
    private List<MainSelVo> list;
}
