package com.green.babyfood.admin;

import com.green.babyfood.admin.model.*;
import com.green.babyfood.search.EnToKo.EnToKo;
import com.green.babyfood.util.FileUtils;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scala.collection.Seq;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ProductImgPkFull insWebEditorImg(MultipartFile img, Long productId) {

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
        ProductImgPkFull full=new ProductImgPkFull();
        full.setPImgId(dto.getPImgId());
        String fullPath="http://192.168.0.144:5001/img/webeditor/"+productId+"/"+randomName;
        full.setImg(fullPath);
       return full;
    }


    public List<ProductImgPkFull> insWebEditorImgList(List<MultipartFile> img, Long productId) {
        String path = getAbsolutePath(fileDir) + "/webeditor/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        List<ProductImgPkFull> list = new ArrayList();
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
            ProductImgPkFull full=new ProductImgPkFull();
            full.setPImgId(dto.getPImgId());
            String fullPath="http://192.168.0.144:5001/img/webeditor/"+productId+"/"+randomName;
            full.setImg(fullPath);
            list.add(full);
        }
        return list;

    }


    public int updProduct(AdminProductUpdDto dto) {
        // 최종 상품 등록할때 사용되는 메소드
        if (dto.getCategory() > 4 && dto.getCategory() > 0){
            log.info("카테고리는 1-4까지 설정 가능, 확인 후 다시 입력하세요");
            return 0;
        }

//        int cateCount = dto.getCateDetail().size();
//        mapper.insCateProduct(cateCount); // 카테고리 갯수만큼 행 생성

        return mapper.updAdminProduct(dto);
    }

    public int changeProduct(AdminProductUpdDto dto) {
        // 상품 정보 수정
        if (dto.getCategory() > 4 && dto.getCategory() > 0){
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

    public List<AdminProductEntity> getProduct(int productId) {
        return mapper.getProduct(productId);
    }

    public int delAdminProduct(int productId) {
        return mapper.delAdminProduct(productId);
    }

    public AdminProductUpdDto updProductInfo(int productId) {
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

    public List<ProductImgPkFull> insImgList(List<MultipartFile> img, Long productId) {
        String path = getAbsolutePath(fileDir) + "/product/" + productId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        List<ProductImgPkFull> list = new ArrayList();
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
            mapper.insImgList(dto);
            ProductImgPkFull full=new ProductImgPkFull();
            full.setPImgId(dto.getPImgId());
            String fullPath="http://192.168.0.144:5001/img/product/"+productId+"/"+randomName;
            full.setImg(fullPath);
            list.add(full);
        }
        return list;
    }

    public AdminSearchSelEntity selproduct(String keyword, int page, int row) {
        AdminSearchSelDto dto = new AdminSearchSelDto();
        dto.setPage(page);
        dto.setRow(row);
        String allergy = "";

        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        String msg = "";
        boolean isEnglish = true;

        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        String typoText = keyword;
        Matcher m = p.matcher(typoText);
        isEnglish = m.find();
        if(isEnglish) {
            msg = EnToKo.engToKor(typoText);
        } else {
            msg = typoText;
        }

        CharSequence normalized = TwitterKoreanProcessorJava.normalize(msg);

        // Tokenize
        Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);

        Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
        List<String> text = TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed);

        StringBuffer sb = new StringBuffer();

        if ( text.size() > 0){
            for (int i = 0; i <text.size()-1; i++) {
                sb.append(text.get(i)).append("|");
            }
        }
        sb.append(text.get(text.size()-1));

        dto.setMsg(String.valueOf(sb));

        //List<SearchSelVo> productDto = mapper.selproduct(dto);

        List<AdminSearchSelVo> productDto = mapper.selproduct(dto);

        for (int i = 0; i <productDto.size(); i++) {
            String thumbnail = productDto.get(i).getThumbnail();
            int productid = productDto.get(i).getProductid();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productid+"/"+thumbnail;
            productDto.get(i).setThumbnail(fullPath);
        }

        int num = mapper.maxpage(String.valueOf(sb),allergy);
        int maxpage = (int) Math.ceil((double) num / row);
        AdminSearchSelEntity entity = new AdminSearchSelEntity();
        entity.setDto(productDto);
        entity.setCount(num);
        entity.setMaxpage(maxpage);

        return entity;
    }

}

