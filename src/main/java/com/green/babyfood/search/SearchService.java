package com.green.babyfood.search;

import com.green.babyfood.search.EnToKo.EnToKo;
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

        int num = mapper.maxpage(allergy);
        int maxpage = (int) Math.ceil((double) num / row);



        SearchSelRes res = new SearchSelRes();
        res.setDto(productDto);
        res.setMaxpage(maxpage);

        return res;
    }

    public SearchSelRes selfilter(String product, int page, int row, int sorter,
                                      String egg, String milk, String buckwheat, String peanut, String soybean, String wheat
            , String pine_nut, String walnut, String crab, String shrimp, String squid, String mackerel, String shellfish, String peach
            , String tomato, String chicken, String pork, String beef, String sulfur_dioxide, String fish){


        StringBuffer allergy = new StringBuffer();

        allergy.append(egg+",").append(milk+",").append(buckwheat).append(",").append(peanut+",").append(soybean + ",")
                .append(wheat+",").append(pine_nut+",").append(walnut+",").append(crab+",").append(shrimp+",").append(squid+",")
                .append(mackerel+",").append(shellfish+",").append(peach+",").append(tomato+",").append(chicken+",").append(pork+",")
                .append(beef+",").append(sulfur_dioxide+",").append(fish+",");
        String strallergy = String.valueOf(allergy);
        String[] split = strallergy.split(",");
        String plus="";
        for (String s : split) {
            if(!s.equals("null")){
                plus+=s+",";
            }
        }
        String subAllergy = plus.substring(0, plus.length()-1);

        SearchSelDto dto = new SearchSelDto();
        dto.setPage(page);
        dto.setRow(row);
        dto.setAllergy(subAllergy);
        dto.setSorter(sorter);

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
        //List<String> text = TwitterKoreanProcessorJava.tokensToJavaStringList(tokens);

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

        int num = mapper.maxpage(String.valueOf(allergy));
        int maxpage = (int) Math.ceil((double) num / row);

        SearchSelRes res = new SearchSelRes();
        res.setDto(productDto);
        res.setMaxpage(maxpage);

        return res;

    }
}
