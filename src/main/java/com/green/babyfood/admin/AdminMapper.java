package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    int productIns(AdminProductInsDto dto);
    List<AdminProductEntity> productAll(AdminProductDto dto);
    AdminProductEntity getProduct(int productId);
    //int updAdminProduct(AdminProductUpdDto dto);
    int delAdminProduct(int productId);
    // 검색기능
    List<AdminProductEntity> searchAdminProduct(String keyword);

    // 웹에디터

    int insPk(PkVo pkVo);

    int insWebEditorImg(Long productId,String img);
    int updAdminProduct(AdminProductUpdDto dto);
    int delImg(Long productId);
    int delProduct(Long productId);

    int insWebEditorImgList(List img, Long productId);

    int categorySel(int productId, int category, List<Integer> cateDetail); // 카테고리분류
}
