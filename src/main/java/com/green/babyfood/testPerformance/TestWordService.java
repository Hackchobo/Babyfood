package com.green.babyfood.testPerformance;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.util.List;

@Service
public class TestWordService {
    public void getTestKomoran(String word) {
        long startTime = System.nanoTime(); // 시간체크용
        for (int i=0; i<10; i++) {
            Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
            KomoranResult analyzeResultList = komoran.analyze(word);

            List<Token> tokenList = analyzeResultList.getTokenList();
            int tokenCount = tokenList.size(); // 형태소 개수 카운트

            System.out.println("형태소 개수: " + tokenCount);
            System.out.println(analyzeResultList.getPlainText());
            for (Token token : tokenList) {
                System.out.format("(%2d, %2d) %s/%s\n",
                        token.getBeginIndex(),
                        token.getEndIndex(), token.getMorph(),
                        token.getPos());
            }
            i++;
        }

        // 종료 후 시간
        long endTime = System.nanoTime();
        // 실행 시간 계산 (종료 시간 - 시작 시간) 및 초 단위로 변환
        double executionTimeSeconds = (endTime - startTime) / 1e9;

        System.out.println("메소드 실행 시간: " + executionTimeSeconds + " 초");
    }

    public void getTwiiterTest(String word) {
        long startTime = System.nanoTime(); // 시간체크용
        for (int i = 0; i<10; i++) {
            CharSequence normalized = TwitterKoreanProcessorJava.normalize(word);
            Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);

            Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
            List<String> text = TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed);

            List<KoreanPhraseExtractor.KoreanPhrase> phrases = TwitterKoreanProcessorJava.extractPhrases(tokens, true, true);


            StringBuffer sb = new StringBuffer();

            System.out.println("tokens: "+ tokens);
            System.out.println("stemmed:"+stemmed);
            System.out.println("text: " +text);
            System.out.println("phrases: " + phrases);
            System.out.println(sb);

            i++;
        }
        // 종료 후 시간
        long endTime = System.nanoTime();
        // 실행 시간 계산 (종료 시간 - 시작 시간) 및 초 단위로 변환
        double executionTimeSeconds = (endTime - startTime) / 1e9;

        System.out.println("메소드 실행 시간: " + executionTimeSeconds + " 초");
    }
}
