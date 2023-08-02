package com.green.babyfood.search.model;

import lombok.Data;

@Data
public class SearchSelDto {
    private String msg;
    private int startIdx;
    private int page;
    private int row;
    private int sorter;
    private String allergy;
}
