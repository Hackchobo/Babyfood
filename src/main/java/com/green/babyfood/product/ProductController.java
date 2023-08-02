package com.green.babyfood.product;

import com.green.babyfood.product.model.PkVo;
import com.green.babyfood.product.model.ProductUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @Operation(summary = "웹에디터 pk가져오는 메소드 상품등록할때 바로 pk를 반환한다")
    public Long insPk(@RequestBody PkVo pkVo){
        return service.insPk(pkVo);
    }

    @PostMapping(value = "/img",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "웹에디터 이미지 넣기")
    public int insWebEditorImg(@RequestPart MultipartFile img, @RequestParam Long productId){
        return service.insWebEditorImg(img,productId);
    }


    @PostMapping(value = "/imglist",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "웹에디터 이미지리스트로 넣기")
    public int insWebEditorImgList(@RequestPart List<MultipartFile> img, @RequestParam Long productId){
        return service.insWebEditorImgList(img,productId);
    }



    @PatchMapping
    @Operation(summary = "최종상품등록할때 저장하는 메소드")
    public int insProduct(@RequestBody ProductUpdDto dto){
        return service.updProduct(dto);
    }


    @DeleteMapping
    @Operation(summary = "웹에디터에서 취소를 하면 테이블에서 이미지 데이터와 빈값의 상품테이블 데이터를 삭제")
    public int delProductImg(@RequestParam Long product){
        return service.delProductImg(product);
    }
}
