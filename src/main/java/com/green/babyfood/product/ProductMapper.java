package com.green.babyfood.product;

import com.green.babyfood.product.model.PkVo;
import com.green.babyfood.product.model.ProductUpdDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface ProductMapper {

    int insPk(PkVo pkVo);

    int insWebEditorImg(Long productId,String img);
    int updProduct(ProductUpdDto dto);
    int delImg(Long productId);
    int delProduct(Long productId);

    int insWebEditorImgList(List img,Long productId);
}
