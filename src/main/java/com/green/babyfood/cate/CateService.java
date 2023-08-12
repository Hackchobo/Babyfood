package com.green.babyfood.cate;

import com.green.babyfood.cate.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CateService {

    private final CateMapper mapper;



    public CateSelLevelVo cateSelLevel(CateSelLevelDto dto) {


//       String plus="";
//       String subAllergy="";
//       if(dto.getAllergy()!=null){
//           for (int i = 0; i < dto.getAllergy().size(); i++) {
//               plus+=dto.getAllergy().get(i)+",";
//           }
//       }
//       if(!plus.equals("")){
//           subAllergy = plus.substring(0, plus.length()-1);
//       }
//       else {
//           subAllergy="";
//           subAllethumbnailrgy="";
//       }

        int page = (dto.getPage() - 1) * dto.getRow();
        CateSelLevelDto2 dto2=new CateSelLevelDto2();
        dto2.setCateId(dto.getCateId());
        dto2.setCateDetailId(dto.getCateDetailId());
        dto2.setPage(page);
        dto2.setRow(dto.getRow());
//        dto2.setStrallergy(subAllergy);

        int maxPaigeResult = mapper.cateSelLevelmaxPage(dto2);
        System.out.println("maxPaigeResult = " + maxPaigeResult);
        List<CateSelListVo> cateSelListVos = mapper.cateSelLevel(dto2);
        for (CateSelListVo vo : cateSelListVos) {
            vo.setThumbnail("http://192.168.0.144:5001/img/product/" + vo.getThumbnail());
        }
        System.out.println("cateSelListVos = " + cateSelListVos);
        CateSelLevelVo vo = new CateSelLevelVo();
        int maxPage = (int) Math.ceil(maxPaigeResult / (double) dto.getRow());

        vo.setMaxPaige(maxPage);
        vo.setList(cateSelListVos);
        return vo;
    }


    public List selCateList(){
        List<CateVo> selcate = mapper.selcate();
        System.out.println(selcate);
        List list=new ArrayList();
        for (int i = 0; i < selcate.size(); i++) {
            List<CateDetailVo> cateDetailVos = mapper.selCateList(selcate.get(i).getCateId());

            CateView view=new CateView();
            view.setCateId(selcate.get(i).getCateId());
            view.setCateName(selcate.get(i).getCateName());
            view.setList(cateDetailVos);
            list.add(view);
        }
        System.out.println(list);
    return list;
    }
}
