package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import com.green.babyfood.user.model.CreatePicDto;
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
    public AdminProductEntity getProduct(@RequestParam int productId){
        return service.getProduct(productId);
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

    @PostMapping(value = "/product/list", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "최종상품등록할때 저장하는 메소드")
    public int insProduct(@RequestBody AdminProductUpdDto dto, @RequestPart List<MultipartFile> thumbnail){
        return service.updProduct(dto, thumbnail);
    }

    @GetMapping("/product/upd/get")
    @Operation(summary = "상품 수정 버튼 메소드", description = "수정버튼 클릭시 기존 상품의 정보를 가져온다<br>"+
    "상품코드(pk) 입력해주세요")
    public AdminProductUpdDto updProductInfo(@RequestParam int productId){
        return service.updProductInfo(productId);
    }

    @PatchMapping("/product/upd")
    @Operation(summary = "상품 수정 메소드")
    public int updProduct(@RequestBody AdminProductUpdDto dto){
        return service.changeProduct(dto);
    }


    @DeleteMapping
    @Operation(summary = "웹에디터에서 취소를 하면 테이블에서 이미지 데이터와 빈값의 상품테이블 데이터를 삭제")
    public int delProductImg(@RequestParam Long product){
        return service.delProductImg(product);
    }

    // 테스트용 사진등록 메소드
    @PostMapping(value = "/imgtest",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "테스트용 사진등록",description =
            "<br>"
    )
    public int postPic(@RequestPart MultipartFile pic, @RequestParam Long iuser){
        CreatePicDto dto = new CreatePicDto();
        dto.setIuser(iuser);
        return service.updPicTest(pic, dto);
    }


}
