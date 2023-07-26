package com.green.babyfood.research;

import com.green.babyfood.research.model.ProductDto;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResearchService {
    private final ResearchMapper mapper;

    List<ProductDto> selproduct(String msg){

//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//        KomoranResult analyzeResultList = komoran.analyze(msg);
//
//        List<Token> tokenList = analyzeResultList.getTokenList();

//        for (Token token : tokenList) {
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//        }

//        List<ProductDto> list=new ArrayList();
//        ProductDto dto = new ProductDto();
//
//        for (int i = 0; i < tokenList.size(); i++) {
//            ProductDto selproduct = mapper.selproduct(tokenList.get(i).getMorph());
//            System.out.println(selproduct);
//            list.add(selproduct);
//        }
//        for (int i = 0; i <list.size(); i++) {
//            System.out.print(list.get(i).getProductid());
//        }
//
//
//        List<ProductDto>list2 = new ArrayList();
//
//        for (int i = 0; i < list.size() - 1; i++) {
//            if (list.get(i).getProductid() != list.get(i+1).getProductid()) {
//                list2.add(list.get(i));
//            }
//        }
//
//        for (int i = 0; i <list2.size(); i++) {
//            System.out.print(list2.get(i).getProductid());
//        }

        List<ProductDto> selproduct = mapper.selproduct(msg);
        return selproduct;
    }
}
