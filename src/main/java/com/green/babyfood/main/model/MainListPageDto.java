package com.green.babyfood.main.model;

import lombok.Data;

import java.util.List;

@Data
public class MainListPageDto {

    private int page;
    private int row;
    private List<String> allergy;

}
