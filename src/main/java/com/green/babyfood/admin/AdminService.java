package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import com.green.babyfood.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.green.babyfood.util.FileUtils.getAbsolutePath;
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper mapper;

    public List<AdminProductEntity> productAll(AdminProductDto dto) {
        int ROW_PER_PAGE = dto.getRow();
        int startIdx = (dto.getPage() - 1) * ROW_PER_PAGE;
        dto.setRowLen(ROW_PER_PAGE);
        dto.setStartIdx(startIdx);
        return mapper.productAll(dto);
    }


    @Value("${file.dir}")
    private String fileDir;

    public Long insPk(PkVo pkVo) {
        mapper.insPk(pkVo);
        return pkVo.getProductId();
    }

    public Long insWebEditorImg(MultipartFile img, Long productId) {

        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String randomName = FileUtils.makeRandomFileNm(img.getOriginalFilename());
        String fileUpload = path + "/" + randomName;
        File file1 = new File(fileUpload);
        try {
            img.transferTo(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdminProductImgDto dto=new AdminProductImgDto();
        dto.setProductId(productId);
        dto.setRandomName(randomName);

        mapper.insWebEditorImg(dto);
       return dto.getPImgId();
    }


    public List insWebEditorImgList(List<MultipartFile> img, Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        List<Long> list = new ArrayList();
        for (MultipartFile imgfile : img) {
            String randomName = FileUtils.makeRandomFileNm(imgfile.getOriginalFilename());
            String fileUpload = path + "/" + randomName;
            File file1 = new File(fileUpload);
            try {
                imgfile.transferTo(file1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AdminProductImgDto dto=new AdminProductImgDto();
            dto.setProductId(productId);
            dto.setRandomName(randomName);
            mapper.insWebEditorImgList(dto);
            list.add(dto.getPImgId());
        }
        return list;

    }


    public int updProduct(AdminProductUpdDto dto) {
        // 최종 상품 등록할때 사용되는 메소드
        if (dto.getCategory() > 4){
            log.info("카테고리는 1-4까지 설정 가능, 확인 후 다시 입력하세요");
            return 0;
        }

        return mapper.updAdminProduct(dto);
    }

    public int changeProduct(AdminProductUpdDto dto) {
        // 상품 정보 수정
        if (dto.getCategory() > 4){
            log.info("카테고리는 1-4까지 설정 가능, 확인 후 다시 입력하세요");
            return 0;
        }
        return mapper.changeAdminProduct(dto);
    }

    public int delProductImg(Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        FileUtils.delFolder(path);

        mapper.delImg(productId);
        return mapper.delProduct(productId);

    }

    public AdminProductEntity getProduct(int productId) {
        return mapper.getProduct(productId);
    }

    public int delAdminProduct(int productId) {
        return mapper.delAdminProduct(productId);
    }

    public List<AdminProductEntity> searchAdminProduct(String keyword) {
        //입력된 모든 단어가 영어인지 검사
        for (char c : keyword.toCharArray()) {
            if (!Character.isLetter(c) || !Character.isAlphabetic(c)) {
                // 영어 아닌 단어 섞여있으면 변환 필요없이 바로 종료
                break;
            } else {
                // 영어로만 이루어진 단어
                // 한글로 변환 - 쿼티 키보드 기준으로 변환 필요

            }
        }
        return mapper.searchAdminProduct(keyword);
    }

    public AdminProductUpdDto updProductInfo(int productId) {
        log.info("카테고리 정보 획득 ");
        List<Integer> cateDetailList = mapper.updProductInfoCate(productId); // 카테고리 정보 획득
        AdminProductUpdDto adminProductUpdDto = mapper.updProductInfo(productId); // 상품 정보 획득
        adminProductUpdDto.setCateDetail(cateDetailList); // 카테고리 정보를 AdminProductUpdDto에 설정
        return adminProductUpdDto;
    }

    public int delWebEditorCancel(Long pImgId){
        ProductImgPk productImgPk = mapper.selProductImgPk(pImgId);
        System.out.println(productImgPk.getImg());
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productImgPk.getProductId()+"/"+productImgPk.getImg();
        File file=new File(path);
        file.delete();
        return mapper.delWebEditorCancel(pImgId);
    }
}

