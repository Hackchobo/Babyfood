package com.green.babyfood.search;

import com.green.babyfood.search.EnToKo.EnToKo;
import com.green.babyfood.search.model.*;

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


import java.util.LinkedList;
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
        dto.setWord(text.get(0).toString());
        dto.setMsg(String.valueOf(sb));

        List<SearchSelVo> productDto = mapper.selproduct(dto);

        for (int i = 0; i <productDto.size(); i++) {
            String thumbnail = productDto.get(i).getThumbnail();
            int productid = productDto.get(i).getProductid();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productid+"/"+thumbnail;
            productDto.get(i).setThumbnail(fullPath);
            String cateId = productDto.get(i).getCateId();
            String name = productDto.get(i).getName();
            StringBuffer productname = new StringBuffer();
            productname.append("[").append(cateId).append("단계] ").append(name);
             productDto.get(i).setName(String.valueOf(productname));
        }

        List<SearchSelproduct> list = new LinkedList<>();

        for (int i = 0; i <productDto.size(); i++) {
            SearchSelproduct selproduct = new SearchSelproduct();
            selproduct.setProductid(productDto.get(i).getProductid());
            selproduct.setName(productDto.get(i).getName());
            selproduct.setThumbnail(productDto.get(i).getThumbnail());
            selproduct.setPrice(productDto.get(i).getPrice());
            list.add(selproduct);
        }

        int num = mapper.maxpage(text.get(0).toString(),String.valueOf(sb),allergy);
        int maxpage = (int) Math.ceil((double) num / row);
        SearchSelRes res = new SearchSelRes();
        res.setDto(list);
        res.setCount(num);
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
        dto.setWord(text.get(0).toString());
        dto.setMsg(String.valueOf(sb));


        List<SearchSelVo> productDto = mapper.selfilter(dto);


        for (int i = 0; i <productDto.size(); i++) {
            String thumbnail = productDto.get(i).getThumbnail();
            int productid = productDto.get(i).getProductid();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productid+"/"+thumbnail;
            productDto.get(i).setThumbnail(fullPath);

            String cateId = productDto.get(i).getCateId();
            String name = productDto.get(i).getName();
            StringBuffer productname = new StringBuffer();
            productname.append("[").append(cateId).append("단계] ").append(name);
            productDto.get(i).setName(String.valueOf(productname));
        }

        List<SearchSelproduct> list = new LinkedList<>();

        for (int i = 0; i <productDto.size(); i++) {
            SearchSelproduct selproduct = new SearchSelproduct();
            selproduct.setProductid(productDto.get(i).getProductid());
            selproduct.setName(productDto.get(i).getName());
            selproduct.setThumbnail(productDto.get(i).getThumbnail());
            selproduct.setPrice(productDto.get(i).getPrice());
            list.add(selproduct);
        }

        int num = mapper.maxpage(text.get(0).toString(),String.valueOf(sb), String.valueOf(allergy));
        int maxpage = (int) Math.ceil((double) num / res.getRow());

        SearchSelRes selres = new SearchSelRes();
        selres.setDto(list);
        selres.setCount(num);
        selres.setMaxpage(maxpage);
        return selres;

    }
}
