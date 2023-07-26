package com.green.babyfood.util;

import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {


    public static String getExt(String ext) {
        return ext.substring(ext.lastIndexOf(".")+1);
    } // 확장자를 뽑아 내는 부분

    public static String makeRandomFileNm(String fileNm){
        return UUID.randomUUID()+"."+getExt(fileNm);
    } // UUID를 사용한 랜덤한 이름을 생성하고 getExt를 이용하여 확장자까지 넣는 부분

    //절대경로 리턴
    public static String getAbsolutePath(String src) {
        return Paths.get(src).toFile().getAbsolutePath(); // 내가 실행하는 드라이버를 자동으로 찍어주는 구문이다 (ex: C: , D:)
    }
}
