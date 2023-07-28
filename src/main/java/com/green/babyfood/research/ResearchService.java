package com.green.babyfood.research;

import com.green.babyfood.research.EnToKo.EnToKo;
import com.green.babyfood.research.model.ProductDto;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
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
public class ResearchService {
    private final ResearchMapper mapper;





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

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(msg2);

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }

        String str = "";

        if (tokenList.size()!=1) {
            for (int i = 0; i < tokenList.size() - 1; i++) {
                str += tokenList.get(i).getMorph() + "|";
            }
        }
        str += tokenList.get(tokenList.size()-1).getMorph();
        log.info("str: ", str);
        System.out.println("str: "+str);
        List<ProductDto> productDtos = mapper.selproduct(str);
        return productDtos;
    }
}
