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
    int updAdminProduct(AdminProductUpdDto dto); // 상품 등록
    int changeAdminProduct(AdminProductUpdDto dto); // 상품 수정
    AdminProductUpdDto updProductInfo(int productId); // 상품 수정버튼 클릭시 기존 정보 가져오기
    List<Integer> updProductInfoCate(int productId);
    int delImg(Long productId);
    int delProduct(Long productId);

    int insWebEditorImgList(List img, Long productId);

}
