package com.green.babyfood.search;

import com.green.babyfood.search.EnToKo.EnToKo;
import com.green.babyfood.search.model.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SearchService {
    private final SearchtMapper mapper;
    List<ProductDto> selproduct(String msg){
        String msg2 = "";
        boolean isEnglish = true;

        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        String typoText = msg;
        Matcher m = p.matcher(typoText);
        isEnglish = m.find();
        if(isEnglish) {
            msg2 = EnToKo.engToKor(typoText);
        } else {
            msg2 = typoText;
        }

//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//        KomoranResult analyzeResultList = komoran.analyze(msg2);
//
//        List<Token> tokenList = analyzeResultList.getTokenList();
//
//        StringBuffer sb = new StringBuffer();
//
//
//        if (tokenList.size()!=1) {
//            for (int i = 0; i < tokenList.size() - 1; i++) {
//                sb.append(tokenList.get(i).getMorph() + "|");
//            }
//        }
//        StringBuffer append = sb.append(tokenList.get(tokenList.size() - 1).getMorph());
//        String str = String.valueOf(append);
//
//        log.info("str: ", str);
//        System.out.println("str: "+str);
        List<ProductDto> productDtos = mapper.selproduct(msg2);

        return productDtos;
    }
}
