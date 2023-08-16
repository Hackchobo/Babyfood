package com.green.babyfood.main;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.main.model.MainListPageDto;
import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MainService {

    private final MainMapper mapper;
    private final AuthenticationFacade USERPK;

    public MainSelVoMaxPaige mainSelView(int page,int row) {

//      String plus="";
//      String subAllergy="";
//      if(dto.getAllergy()!=null){
//          for (int i = 0; i < dto.getAllergy().size(); i++) {
//              plus+=dto.getAllergy().get(i)+",";
//          }
//          subAllergy = plus.substring(0, plus.length() - 1);
//      }


        int startIdx = (page - 1) * row;

       List<MainSelVo> mainSelVos = mapper.mainSelView(startIdx,row);
       for (int i = 0; i < mainSelVos.size(); i++) {
           String thumbnail = mainSelVos.get(i).getThumbnail();
           Long productId=mainSelVos.get(i).getProductId();
           String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
           mainSelVos.get(i).setThumbnail(fullPath);
           Long levelSel = mapper.levelSel(mainSelVos.get(i).getProductId());
           if(levelSel==null){
               continue;
           }
           String name = mainSelVos.get(i).getName();
           String levelName="["+levelSel+"단계] "+name;
           mainSelVos.get(i).setName(levelName);
       }



      int maxPaige1 = mapper.maxPage();
      int maxPaige2 = (int) Math.ceil((double) maxPaige1 / row);
      MainSelVoMaxPaige mainSelVoMaxPaige = new MainSelVoMaxPaige();
      mainSelVoMaxPaige.setMaxPage(maxPaige2);
      mainSelVoMaxPaige.setPageCount(maxPaige1);
      mainSelVoMaxPaige.setList(mainSelVos);

     return mainSelVoMaxPaige;
    }




    public List<MainSelVo> random() {



//       String strallergy = String.valueOf(allergy);
//       String[] split = strallergy.split(",");
//       String plus = "";
//       for (String s : split) {
//           if (!s.equals("null")) {
//               plus += s + ",";
//           }
//       }

        List<MainSelVo> random = mapper.random();
        for (int i = 0; i < random.size(); i++) {
            String thumbnail = random.get(i).getThumbnail();
            Long productId=random.get(i).getProductId();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
            random.get(i).setThumbnail(fullPath);
            Long levelSel = mapper.levelSel(random.get(i).getProductId());
            if(levelSel==null){
                continue;
            }
            String name = random.get(i).getName();
            String levelName="["+levelSel+"단계] "+name;
            random.get(i).setName(levelName);
        }
        return random;
    }


    public List<MainSelVo> bestSell() {

//       String plus="";
//       String subAllergy="";
//       if(allergy!=null){
//           for (int i = 0; i < allergy.size(); i++) {
//               plus+=allergy.get(i)+",";
//           }
//       }

//      if(!plus.equals("")){
//          subAllergy = plus.substring(0, plus.length()-1);

//          return mapper.bestSell(subAllergy);
//      }

        List<MainSelVo> mainSelVos = mapper.bestSell();
        for (int i = 0; i < mainSelVos.size(); i++) {
            String thumbnail = mainSelVos.get(i).getThumbnail();
            Long productId=mainSelVos.get(i).getProductId();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
            mainSelVos.get(i).setThumbnail(fullPath);
            Long levelSel = mapper.levelSel(mainSelVos.get(i).getProductId());
            if(levelSel==null){
                continue;
            }
            String name = mainSelVos.get(i).getName();
            String levelName="["+levelSel+"단계] "+name;
            mainSelVos.get(i).setName(levelName);

        }
        return mainSelVos;
    }



    public MainSelVoMaxPaige bestSellAll(int page,int row) {



//       String plus="";
//       String subAllergy="";
//       if(allergy!=null){
//           for (int i = 0; i < allergy.size(); i++) {
//               plus+=allergy.get(i)+",";
//           }
//       }

        int startIdx=(page-1)*row;
//      if(!plus.equals("")){
//            subAllergy = plus.substring(0, plus.length()-1);
//           System.out.println(subAllergy);
//           int maxPageCount = mapper.bestSellAllMaxPage(subAllergy);
//           int maxPage=(int)Math.ceil(maxPageCount/(double)row);

//           List<MainSelVo> mainSelVos = mapper.bestSellAll(startIdx, row);
//           for (int i = 0; i < mainSelVos.size(); i++) {
//               String thumbnail = mainSelVos.get(i).getThumbnail();
//               Long productId=mainSelVos.get(i).getProductId();
//               String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
//               mainSelVos.get(i).setThumbnail(fullPath);
//           }

//           MainSelVoMaxPaige mainSelVoMaxPaige=new MainSelVoMaxPaige();
//           mainSelVoMaxPaige.setMaxPage(maxPage);
//           mainSelVoMaxPaige.setList(mainSelVos);

//           return mainSelVoMaxPaige;
//       }

        int maxPageCount = mapper.bestSellAllMaxPage();
        int maxPage=(int)Math.ceil(maxPageCount/(double)row);
        List<MainSelVo> mainSelVos = mapper.bestSellAll(startIdx, row);
        for (int i = 0; i < mainSelVos.size(); i++) {
            String thumbnail = mainSelVos.get(i).getThumbnail();
            Long productId=mainSelVos.get(i).getProductId();
            String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
            mainSelVos.get(i).setThumbnail(fullPath);
            Long levelSel = mapper.levelSel(mainSelVos.get(i).getProductId());
            if(levelSel==null){
                continue;
            }
            String name = mainSelVos.get(i).getName();
            String levelName="["+levelSel+"단계] "+name;
            mainSelVos.get(i).setName(levelName);
        }
        MainSelVoMaxPaige mainSelVoMaxPaige=new MainSelVoMaxPaige();
        mainSelVoMaxPaige.setMaxPage(maxPage);
        mainSelVoMaxPaige.setPageCount(maxPageCount);
        mainSelVoMaxPaige.setList(mainSelVos);
        return mainSelVoMaxPaige;
}





//   public MainSelVoMaxPaige birthRecommend(Long iuser, int page, int row) {
//       int month = mapper.birth(iuser);
//       int cate = 0;
//       if (month <= 4) {
//           throw new RuntimeException("이유식 먹을수 있는 나이가 아닙니다");
//       }
//       if (month > 4 && month <= 6) {
//           cate = 1;
//       } else if (month > 6 && month <= 10) {
//           cate = 2;
//       } else if (month > 10 && month <= 13) {
//           cate = 3;
//       } else if (month > 13) {
//           cate = 4;
//       }
//       int startIdx = (page - 1) * row;
//       int count = mapper.birthMaxPage(cate);
//       int maxPage = (int) (Math.ceil(count / (double) row));
//       MainSelVoMaxPaige voMaxPaige = new MainSelVoMaxPaige();
//       List<MainSelVo> mainSelVos = mapper.birthRecommend(cate, startIdx, row);
//       voMaxPaige.setMaxPage(maxPage);
//       voMaxPaige.setList(mainSelVos);
//       return voMaxPaige;
//   }


    public List<MainSelVo> birthRecommendFilter(int row) {

//        String plus="";
//        String subAllergy="";
//        if(allergy!=null){
//            for (int i = 0; i < allergy.size(); i++) {
//                plus+=allergy.get(i)+",";
//            }
//        }
//
//        if(!plus.equals("")){
//             subAllergy = plus.substring(0, plus.length()-1);
//        }
//        else {
//            subAllergy="";
//        }
        int month = mapper.birth(USERPK.getLoginUserPk());
        int cate = 0;
        if (month <= 4) {
             throw new RuntimeException("이유식 먹을수 있는 나이가 아닙니다");
        }
        if (month > 4 && month <= 6) {
            cate = 1;
        } else if (month > 6 && month <= 10) {
            cate = 2;
        } else if (month > 10 && month <= 13) {
            cate = 3;
        } else if (month > 13) {
            cate = 4;
        }
//        int startIdx = (page - 1) * row;
//      int count = mapper.birthRecommendFilterMaxPaige(cate,subAllergy);
//      int maxPage = (int) (Math.ceil(count / (double) row));
//      MainSelVoMaxPaige voMaxPaige = new MainSelVoMaxPaige();
        List<MainSelVo> mainSelVos = mapper.birthRecommendFilter(cate, row);
        for (int i = 0; i < mainSelVos.size(); i++) {
            String thumbnail = mainSelVos.get(i).getThumbnail();
            Long productId=mainSelVos.get(i).getProductId();
           String fullPath ="http://192.168.0.144:5001/img/product/"+productId+"/"+thumbnail;
           mainSelVos.get(i).setThumbnail(fullPath);
            Long levelSel = mapper.levelSel(mainSelVos.get(i).getProductId());
            if(levelSel==null){
                continue;
            }
            String name = mainSelVos.get(i).getName();
            String levelName="["+levelSel+"단계] "+name;
            mainSelVos.get(i).setName(levelName);
        }
//       voMaxPaige.setMaxPage(maxPage);
//       voMaxPaige.setList(mainSelVos);
//       return voMaxPaige;
        return mainSelVos;
    }
}