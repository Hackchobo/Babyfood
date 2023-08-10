package com.green.babyfood.search;

import com.green.babyfood.search.EnToKo.EnToKo;
import com.green.babyfood.search.model.SearchRes;
import com.green.babyfood.search.model.SearchSelDto;
import com.green.babyfood.search.model.SearchSelRes;
import com.green.babyfood.search.model.SearchSelVo;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import scala.collection.Seq;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SearchService {
    private final SearchMapper mapper;

    public SearchSelRes selproduct(String product, int page, int row){
        SearchSelDto dto = new SearchSelDto();
        dto.setPage(page);
        dto.setRow(row);
        String allergy = "";

        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        String msg = "";
        boolean isEnglish = true;

        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        String typoText = product;
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

        List<SearchSelVo> productDto = mapper.selproduct(dto);

        for (int i = 0; i <productDto.size(); i++) {
            String thumbnail = productDto.get(i).getThumbnail();
            int productid = productDto.get(i).getProductid();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productid+"/"+thumbnail;
            productDto.get(i).setThumbnail(fullPath);
        }

        int num = mapper.maxpage(allergy);
        int maxpage = (int) Math.ceil((double) num / row);



        SearchSelRes res = new SearchSelRes();
        res.setDto(productDto);
        res.setMaxpage(maxpage);

        return res;
    }

    public SearchSelRes selfilter(SearchRes res){
        StringBuffer allergy = new StringBuffer();
        String strallergy = "";
        String plus="";

        if (res.getFilter().size()> 1){
            for (int i = 0; i < res.getFilter().size()-1; i++) {
                allergy.append(res.getFilter().get(i)+",");
            }
        }
        if (res.getFilter().size()>0){
            allergy.append(res.getFilter().get(res.getFilter().size()-1));
        }

        strallergy = String.valueOf(allergy);

        SearchSelDto dto = new SearchSelDto();
        dto.setPage(res.getPage());
        dto.setRow(res.getRow());
        dto.setAllergy(strallergy);
        dto.setSorter(res.getSorter());

        System.out.println(dto.getAllergy());

        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        String msg = "";
        boolean isEnglish = true;

        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        String typoText = res.getProduct();
        Matcher m = p.matcher(typoText);
        isEnglish = m.find();
        if(isEnglish) {
            msg = EnToKo.engToKor(typoText);
        } else {
            msg = typoText;
        }

        CharSequence normalized = TwitterKoreanProcessorJava.normalize(msg);

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


        List<SearchSelVo> productDto = mapper.selfilter(dto);


        for (int i = 0; i <productDto.size(); i++) {
            String thumbnail = productDto.get(i).getThumbnail();
            int productid = productDto.get(i).getProductid();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productid+"/"+thumbnail;
            productDto.get(i).setThumbnail(fullPath);
        }

        int num = mapper.maxpage(String.valueOf(allergy));
        int maxpage = (int) Math.ceil((double) num / res.getRow());

        SearchSelRes selres = new SearchSelRes();
        selres.setDto(productDto);
        selres.setMaxpage(maxpage);


        return selres;

    }
}
