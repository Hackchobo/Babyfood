package com.green.babyfood.search.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchRes {
    private String product;
    private int row;
    private int page;
    private int sorter;
    List<String> filter;
}
