package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MypageService {
    private final MypageMapper mapper;

    List<SelMypageOrderListDto> mypageOrderlist(int iuser){
        List<SelMypageOrderListDto> orderlistDtos = mapper.mypageOrderlist(iuser);

        for (int i = 0; i <orderlistDtos.size(); i++) {
            if (orderlistDtos.get(i).getShipment().equals("1")){
                orderlistDtos.get(i).setShipment("상품 준비중");
            } else if (orderlistDtos.get(i).getShipment().equals("2")) {
                orderlistDtos.get(i).setShipment("상품 배송중");
            } else if (orderlistDtos.get(i).getShipment().equals("3")) {
                orderlistDtos.get(i).setShipment("상품 주문취소");
            } else if (orderlistDtos.get(i).getShipment().equals("4")) {
                orderlistDtos.get(i).setShipment("상품 배송완료");
            }
        }
        return orderlistDtos;
    }
    public List<SelOrderlistDto> Orderlist(int iuser){


        return mapper.Orderlist(iuser);
    }
    public List<SelOrderlistDto> selOneMonths(SelOrderlistMonthsDto dto){
        return mapper.selOneMonths(dto);
    }

    SelprofileDto profile(int iuser){
        SelprofileDto profile = mapper.profile(iuser);
        return profile;
    }

    public int UpdProfileDto(UpdProfileDto dto){
        log.info("입력한 닉네임:{}",dto.getNickNm());
        SelNickNmDto selNickNmDto = mapper.SelNickNm(dto.getNickNm());

        if (!(selNickNmDto == null)){
            return -1;
        }

        return mapper.Updprofile(dto);
    }

    public int delUser(int iuser){
        return mapper.delUser(iuser);
    }

}
