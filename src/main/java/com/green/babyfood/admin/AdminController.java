package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "관리자페이지")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService service;


    @GetMapping("/product/all")
    @Operation(summary = "상품 조회", description = "모든 상품을 조회한다 <br>"+
            "page = 페이지값 <br>" +
            "row = 페이지당 게시물 <br>")
    List<AdminProductEntity> productAll(@RequestParam @Min(value = 1, message = "page값은 1이상이어야 합니다.") int page
            ,@RequestParam(defaultValue = "10") int row){
        AdminProductDto dto =new AdminProductDto();
        dto.setPage(page);
        dto.setRow(row);
        return service.productAll(dto);
    }

    @GetMapping("/product/productId")
    @Operation(summary = "상품 상세조회", description = "상품 코드 입력")
    public List<AdminProductEntity> getProduct(@RequestParam int productId){
        return service.getProduct(productId);
    }

    @DeleteMapping ("/product/productId")
    @Operation(summary = "등록된 상품 삭제")
    public int delAdminProduct(@RequestParam int productId){
        return service.delAdminProduct(productId);
    }

    @GetMapping("/product/search")
    @Operation(summary = "검색",description = "상품이름검색")
    public AdminSearchSelEntity getproduct(@RequestParam String keyword,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "30") int row
    ) {
        return service.selproduct(keyword,page,row);
    }



    // 웹에디터


    @PostMapping("/product")
    @Operation(summary = "웹에디터 pk가져오는 메소드 상품등록할때 바로 pk를 반환한다")
    public Long insPk(@RequestBody PkVo pkVo){
        return service.insPk(pkVo);
    }


    @PostMapping(value = "/product/img",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "웹에디터 이미지 넣기",description = ""+
            "img : 이미지 풀 경로<br>"+
            "pimgId : 웹에디터 이미지의 pk값")
    public ProductImgPkFull insWebEditorImg(@RequestPart MultipartFile img, @RequestParam Long productId){
        return service.insWebEditorImg(img,productId);
    }

    @PostMapping(value = "/product/imglist",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "웹에디터 이미지리스트로 넣기",description = ""+
            "img : 이미지 풀 경로<br>"+
            "pimgId : 웹에디터 이미지의 pk값")
    public List<ProductImgPkFull> insWebEditorImgList(@RequestPart List<MultipartFile> img, @RequestParam Long productId){
        return service.insWebEditorImgList(img,productId);
    }

    @GetMapping("/product/modification")
    @Operation(summary = "상품 수정 버튼 메소드", description = "수정버튼 클릭시 기존 상품의 정보를 가져온다<br>"+
    "상품코드(pk) 입력해주세요")
    public AdminProductUpdDto updProductInfo(@RequestParam int productId){
        return service.updProductInfo(productId);
    }

    @PatchMapping("/product/modification")
    @Operation(summary = "상품 수정 메소드", description = "수정할 내역 입력후 클릭하면, 업데이트됩니다.")
    public int updProduct(@RequestBody AdminProductUpdDto dto){
        return service.changeProduct(dto);
    }

    @DeleteMapping("/product/cancelation")
    @Operation(summary = "웹에디터에서 취소를 하면 테이블에서 이미지 데이터와 빈값의 상품테이블 데이터를 삭제")
    public int delProductImg(@RequestParam Long product){
        return service.delProductImg(product);
    }

    @DeleteMapping("/product/cancelation/editor")
    @Operation(summary = "웹에디터 등록하기전 이미지 삭제")
    public int delProductWebImg(@RequestParam Long pImgId){
        return service.delWebEditorCancel(pImgId);
    }

    @PostMapping(value = "/product/imglist/thumbnail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "썸네일 이미지리스트로 넣기", description = "본문 등록할 때 함께 보내주세요"+
            "img : 이미지<br>"+
            "pimgId : 웹에디터 이미지의 pk값<br>" +
            "productId : 등록할 상품 pk값" )
    public List<ProductImgPkFull> insImgList(@RequestPart List<MultipartFile> img, @RequestParam Long productId){
        return service.insImgList(img,productId);
    }

    @PatchMapping("/product/registration")
    @Operation(summary = "최종상품등록할때 저장하는 메소드", description = "상품등록 마지막단계 <br>" +
                    "상품 내용이 DB에 등록됩니다")
    public int insProduct(@RequestBody AdminProductUpdDto dto){
        log.info("테스트");
        return service.updProduct(dto);
    }
}
