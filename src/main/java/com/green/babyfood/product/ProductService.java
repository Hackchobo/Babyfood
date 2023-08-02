package com.green.babyfood.product;

import com.green.babyfood.product.model.PkVo;
import com.green.babyfood.product.model.ProductUpdDto;
import com.green.babyfood.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.green.babyfood.util.FileUtils.getAbsolutePath;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductMapper mapper;

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
        String randomName= FileUtils.makeRandomFileNm(img.getOriginalFilename());
        String fileUpload=path+"/"+randomName;
        File file1=new File(fileUpload);
        try {
            img.transferTo(file1);
        }catch (Exception e){
            e.printStackTrace();
        }
       return mapper.insWebEditorImg(productId,randomName);
    }

    public int insWebEditorImgList(List<MultipartFile> img, Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        List list=new ArrayList();
        for (MultipartFile imgfile:img) {
            String randomName= FileUtils.makeRandomFileNm(imgfile.getOriginalFilename());
            String fileUpload=path+"/"+randomName;
            File file1=new File(fileUpload);
            try {
                imgfile.transferTo(file1);
            }catch (Exception e){
                e.printStackTrace();
            }

            list.add(randomName);
        }
        return mapper.insWebEditorImgList(list,productId);

    }




    public int updProduct(ProductUpdDto dto){
        return mapper.updProduct(dto);
    }


    public int delProductImg(Long productId){
        String path=getAbsolutePath(fileDir) + "/webeditor/" + productId;
        FileUtils.delFolder(path);

        mapper.delImg(productId);
      return mapper.delProduct(productId);

    }
}
