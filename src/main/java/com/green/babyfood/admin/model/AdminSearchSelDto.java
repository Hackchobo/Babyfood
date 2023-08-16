package com.green.babyfood.admin.model;

import lombok.Data;

@Data
public class AdminSearchSelDto {
    private String msg;
    private int startIdx;
    private int page;
    private int row;
    private int sorter;
    private String allergy;
}
