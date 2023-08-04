package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import com.green.babyfood.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.green.babyfood.util.FileUtils.getAbsolutePath;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper mapper;

    int productIns(AdminProductInsDto dto) {
        return mapper.productIns(dto);
    }

    public List<AdminProductEntity> productAll(AdminProductDto dto) {
        int ROW_PER_PAGE = dto.getRow();
        int startIdx = (dto.getPage() - 1) * ROW_PER_PAGE;
        dto.setRowLen(ROW_PER_PAGE);
        dto.setStartIdx(startIdx);
        return mapper.productAll(dto);
    }


    // ------------웹에디터----------------

    @Value("${file.dir}")
    private String fileDir;

    public Long insPk(PkVo pkVo) {
        mapper.insPk(pkVo);
        return pkVo.getProductId();
    }

    public int insWebEditorImg(MultipartFile img, Long productId) {

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
        return mapper.insWebEditorImg(productId, randomName);
    }

    public int insWebEditorImgList(List<MultipartFile> img, Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        List list = new ArrayList();
        for (MultipartFile imgfile : img) {
            String randomName = FileUtils.makeRandomFileNm(imgfile.getOriginalFilename());
            String fileUpload = path + "/" + randomName;
            File file1 = new File(fileUpload);
            try {
                imgfile.transferTo(file1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(randomName);
        }
        return mapper.insWebEditorImgList(list, productId);

    }

    public int updProduct(ProductUpdDto dto) {
        return mapper.updAdminProduct(dto);
    }

    public int delProductImg(Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        FileUtils.delFolder(path);

        mapper.delImg(productId);
        return mapper.delProduct(productId);

    }

    public int getProduct(int productId) {
        return mapper.getProduct(productId);
    }

    public int updAdminProduct(AdminProductUpdDto dto) {
        return mapper.updAdminProduct(dto);
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
}

