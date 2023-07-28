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

    public List cateList() {
        List<CateVo> cateVos = mapper.cateAll(); //단계 카테고리
        List<cateDetailVo> cateDetailVos = mapper.cateDetailAll(); //디테일 카테고리

        List list = new ArrayList();
        for (int i = 0; i < cateVos.size(); i++) {
            cateAllVo vo = new cateAllVo();
            vo.setCateId(cateVos.get(i).getCateId());
            vo.setCateName(cateVos.get(i).getCateName());
            vo.setList(cateDetailVos);
            list.add(vo);
        }
        return list;
    }

    public List<CateView> selCateList(){
    return mapper.selCateList();
    }
}
