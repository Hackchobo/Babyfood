package com.green.babyfood.admin.model;

import lombok.Data;

import java.util.List;

@Data
public class AdminSearchSelEntity {
    private int count;
    private int maxpage;
    private List<AdminSearchSelVo> dto;

}
