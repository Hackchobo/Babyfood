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
        int paige = (dto.getPaige() - 1) * dto.getRow();
        dto.setPaige(paige);
        int maxPaigeResult = mapper.cateSelLevelmaxPaige(dto.getCateId(), dto.getCateDetailId());
        List<CateSelListVo> cateSelListVos = mapper.cateSelLevel(dto);

        CateSelLevelVo vo = new CateSelLevelVo();
        int maxPaige = (int) Math.ceil(maxPaigeResult / (double) dto.getRow());

        vo.setMaxPaige(maxPaige);
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
