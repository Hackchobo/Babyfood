package com.green.babyfood.research;

import com.green.babyfood.research.model.ProductDto;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResearchService {
    private final ResearchMapper mapper;

    List<ProductDto> selproduct(String msg){

        List<ProductDto> selproduct = mapper.selproduct(msg);
        return selproduct;
    }



    List<ProductDto> tokenList(String msg){
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(msg);

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }

        StringBuffer sb = new StringBuffer();
        if (! (tokenList.size()==1)){
            for (int i = 0; i < tokenList.size()-1; i++) {
                sb.append(tokenList.get(i)+"|");
            }
        }

        sb.append(tokenList.get(0));

        List<ProductDto> productDtos = mapper.selproduct2(String.valueOf(sb));



        return productDtos;

    }

}
