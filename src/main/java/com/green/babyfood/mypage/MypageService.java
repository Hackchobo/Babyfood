package com.green.babyfood.mypage;

import com.green.babyfood.mypage.model.SelOrderlistDto;
import com.green.babyfood.mypage.model.SelprofileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageService {
    private final MypageMapper mapper;

    List<SelOrderlistDto> mypageOrderlist(int iuser){
        List<SelOrderlistDto> orderlistDtos = mapper.mypageOrderlist(iuser);

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
    SelprofileDto profile(int iuser){
        SelprofileDto profile = mapper.profile(iuser);
        return profile;
    }

}
