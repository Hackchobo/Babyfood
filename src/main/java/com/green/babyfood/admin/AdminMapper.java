package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import com.green.babyfood.user.model.CreatePicDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface AdminMapper {
    int productIns(AdminProductInsDto dto);
    List<AdminProductEntity> productAll(AdminProductDto dto);
    List<AdminProductEntity> getProduct(int productId);
    //int updAdminProduct(AdminProductUpdDto dto);
    int delAdminProduct(int productId);
    // 검색기능
    List<AdminProductEntity> searchAdminProduct(String keyword);

    // 웹에디터

    int insPk(PkVo pkVo);

    int insWebEditorImg(AdminProductImgDto dto);
    int updAdminProduct(AdminProductUpdDto dto); // 상품 등록
    int changeAdminProduct(AdminProductUpdDto dto); // 상품 수정
    AdminProductUpdDto updProductInfo(int productId); // 상품 수정버튼 클릭시 기존 정보 가져오기
    List<Integer> updProductInfoCate(int productId);
    int delImg(Long productId);
    int delProduct(Long productId);

    int insWebEditorImgList(AdminProductImgDto dto);
    int insThumbnail(List<MultipartFile> thumbnail);
    int updPicTest(CreatePicProduct dto);

    int updPicTestThumb(CreatePicProduct dto);
    int delWebEditorCancel(Long pImgId);
    ProductImgPk selProductImgPk(Long pImgId);

    int insImgList(AdminProductImgDto dto);
}
