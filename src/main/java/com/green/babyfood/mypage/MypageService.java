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

    public  OrderlistSelDto[] Orderlist(OrderlistMonthsSelDto dto){

        List<OrderlistCountSelDto> orderlist = mapper.Orderlist(dto);

        OrderlistSelDto[] orderlistSelDto = new OrderlistSelDto[orderlist.size()];


        for (int i = 0; i < orderlist.size(); i++) {

            if (orderlist.get(i).getShipment().equals("1")){
                orderlist.get(i).setShipment("상품 준비중");
            } else if (orderlist.get(i).getShipment().equals("2")) {
                orderlist.get(i).setShipment("상품 배송중");
            } else if (orderlist.get(i).getShipment().equals("3")) {
                orderlist.get(i).setShipment("상품 주문취소");
            } else if (orderlist.get(i).getShipment().equals("4")) {
                orderlist.get(i).setShipment("상품 배송완료");
            }

            if (orderlist.get(i).getCount()>0){
                String name = orderlist.get(i).getName();
                StringBuffer sb = new StringBuffer();
                sb.append(name).append(" 외").append(orderlist.get(i).getCount()).append("개");

                String ordername = String.valueOf(sb);
                orderlist.get(i).setName(ordername);
            }
        }

        for (int i = 0; i < orderlistSelDto.length; i++) {
            orderlistSelDto[i]=new OrderlistSelDto();
            orderlistSelDto[i].setOrderId(orderlist.get(i).getOrderId());
            orderlistSelDto[i].setCreatedAt(orderlist.get(i).getCreatedAt());
            orderlistSelDto[i].setThumbnail(orderlist.get(i).getThumbnail());
            orderlistSelDto[i].setName(orderlist.get(i).getName());
            orderlistSelDto[i].setPrice(orderlist.get(i).getPrice());
            orderlistSelDto[i].setShipment(orderlist.get(i).getShipment());
        }

        return orderlistSelDto;
    }

    public OrderlistSelUserDto OrderlistDetail(int orderId){

        List<OrderlistDetailSelDto> orderlist = mapper.OrderlistDetail(orderId);
        OrderlistUserDto user = mapper.selUser(orderId);
        OrderlistSelUserDto build = OrderlistSelUserDto.builder().orderlist(orderlist).user(user).build();

        return build;
    }

    ProfileSelDto profile(int iuser){
        ProfileSelDto profile = mapper.profile(iuser);
        return profile;
    }

    public int UpdProfileDto(ProfileUpdDto dto){

        log.info("입력한 닉네임:{}",dto.getNickNm());
        MypageNickNmDto selNickNmDto = mapper.SelNickNm(dto.getNickNm());

        if (!(selNickNmDto == null)){
            return 0;
        }

        return mapper.Updprofile(dto);
    }

    public int delUser(int iuser){
        return mapper.delUser(iuser);
    }

}
