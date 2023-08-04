package com.green.babyfood.util;

import java.io.File;
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


    public static void delFolder(String path){
        File file=new File(path);
        if(file.exists() && file.isDirectory()){
            File[] fileArr=file.listFiles(); //폴더안에 파일들을 배열로 받는다
            for (File f:fileArr) {
                if(f.isDirectory()){
                    delFolder(f.getPath());
                }else {
                    f.delete();
                }
            }
        }
        file.delete(); //마지막으로 첫번재폴더를 삭제한다
    }





}
