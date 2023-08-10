package com.green.babyfood.cate.model;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class CateSelLevelDto {

    private Long cateId;
    private Long cateDetailId;
    private int page;
    private int row;
//    private List<String> allergy;


    public CateSelLevelDto() {
        cateDetailId = 0L;
    }
}
