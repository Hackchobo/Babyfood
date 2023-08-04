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



    public CateSelLevelVo cateSelLevel(CateSelLevelDto dto,String egg,String milk,String buckwheat,String peanut,String soybean,String wheat,
                                       String pine_nut,String walnut,String crab,String shrimp,String squid,String mackerel,String shellfish,
                                       String peach, String tomato,String chicken,String pork,String beef,String sulfur_dioxide,String fish) {

        StringBuffer allergy = new StringBuffer();
        allergy.append(egg+",").append(milk+",").append(buckwheat).append(",").append(peanut+",").append(soybean + ",")
                .append(wheat+",").append(pine_nut+",").append(walnut+",").append(crab+",").append(shrimp+",").append(squid+",")
                .append(mackerel+",").append(shellfish+",").append(peach+",").append(tomato+",").append(chicken+",").append(pork+",")
                .append(beef+",").append(sulfur_dioxide+",").append(fish+",");

        String strallergy = String.valueOf(allergy);
        String[] split = strallergy.split(",");
        String plus="";
        String subAllergy="";
        for (String s : split) {
            if(!s.equals("null")){
                plus+=s+",";
            }
        }
        if(!plus.equals("")){
            subAllergy = plus.substring(0, plus.length()-1);
        }
        else {
            subAllergy="";
        }

        int page = (dto.getPage() - 1) * dto.getRow();
        CateSelLevelDto2 dto2=new CateSelLevelDto2();
        dto2.setCateId(dto.getCateId());
        dto2.setCateDetailId(dto.getCateDetailId());
        dto2.setPage(page);
        dto2.setRow(dto.getRow());
        dto2.setStrallergy(subAllergy);

        int maxPaigeResult = mapper.cateSelLevelmaxPage(dto2);
        List<CateSelListVo> cateSelListVos = mapper.cateSelLevel(dto2);

        CateSelLevelVo vo = new CateSelLevelVo();
        int maxPage = (int) Math.ceil(maxPaigeResult / (double) dto.getRow());

        vo.setMaxPaige(maxPage);
        vo.setList(cateSelListVos);
        return vo;
    }


    public List selCateList(){
        List<CateVo> selcate = mapper.selcate();
        List list=new ArrayList();
        for (int i = 0; i < selcate.size(); i++) {
            List<CateDetailVo> cateDetailVos = mapper.selCateList(selcate.get(i).getCateId());
            CateView view=new CateView();
            view.setCateId(selcate.get(i).getCateId());
            view.setCateName(selcate.get(i).getCateName());
            view.setList(cateDetailVos);
            list.add(view);
        }
    return list;
    }
}
