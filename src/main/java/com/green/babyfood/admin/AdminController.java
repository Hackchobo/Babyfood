package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "관리자페이지")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
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

    @GetMapping("/product/get")
    @Operation(summary = "상품 상세조회", description = "상품 코드 입력")
    public int getProduct(@RequestParam int productId){
        return service.getProduct(productId);
    }

    @PostMapping("/product/ins")
    @Operation(summary = "상품 등록", description = ""+
            "title = 타이틀 <br>" +
            "name = 이름 <br>" +
            "price = 가격<br>" +
            "quantity = 재고<br>" +
            "description = 제품설명 <br>" +
            "allergy = 알러지정보 <br>")
    public int productIns(AdminProductInsDto dto){
        return service.productIns(dto);
    }

    @PatchMapping("/product/patch")
    @Operation(summary = "등록된 상품 정보 수정")
    public int patchAdminProduct(AdminProductUpdDto dto){
        return service.updAdminProduct(dto);
    }

    @PatchMapping("/product/delete")
    @Operation(summary = "등록된 상품 삭제")
    public int delAdminProduct(@RequestParam int productId){
        return service.delAdminProduct(productId);
    }

    @GetMapping("/product/search")
    @Operation(summary = "관리자페이지 - 상품 검색", description = "모든 상품(품절된 상품)포함하여 검색 <br>"+
    "띄어쓰기 / 한영타변환 검색기능 지원<br>")
    public List<AdminProductEntity> searchAdminProduct(@RequestParam String keyword){
        return service.searchAdminProduct(keyword);
    }

    // 웹에디터


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
