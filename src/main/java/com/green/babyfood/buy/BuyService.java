package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import com.green.babyfood.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.substring;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyMapper Mapper;
    private final AuthenticationFacade USERPK;

    public BuyProductRes BuyProduct(BuyInsDto dto){

        BuyEntity entity = new BuyEntity();
        entity.setReceiver(dto.getReceiver());
        entity.setAddress(dto.getAddress());
        entity.setAddressDetail(dto.getAddressDetail());
        entity.setPhoneNm(dto.getPhoneNm());
        entity.setRequest(dto.getRequest());
        entity.setPayment(dto.getPayment());
        entity.setIuser(USERPK.getLoginUserPk());
        entity.setPoint(dto.getPoint());

        List<BuyOrderbasketDto> orderbasket = new ArrayList<>();
        for (int i = 0; i <dto.getInsorderbasket().size(); i++) {
            BuyOrderbasketDto orderbasketdto = new BuyOrderbasketDto();
            orderbasketdto.setProductId(dto.getInsorderbasket().get(i).getProductId());
            orderbasketdto.setCartId(dto.getInsorderbasket().get(i).getCartId());
            orderbasketdto.setCount(dto.getInsorderbasket().get(i).getCount());
            orderbasketdto.setIuser(USERPK.getLoginUserPk());
            orderbasketdto.setTotalprice(dto.getInsorderbasket().get(i).getTotalprice());
            orderbasket.add(orderbasketdto);
        }

        final int shipment = 1;
        final float earnedPercent = 0.03F;
        int totalprice = 0;

        BuyUserSelDto userDto = Mapper.selUser(USERPK.getLoginUserPk());

            // 값이 없을때
            if (entity.getRequest().equals("")||entity.getRequest()==null){
                entity.setRequest("요청사항 없음");
            }if (entity.getAddress().equals("")||entity.getAddress()==null) {
                entity.setAddress(userDto.getAddress());
            }if (entity.getAddressDetail().equals("")||entity.getAddressDetail()==null) {
                entity.setAddressDetail(userDto.getAddressDetail());
            }if (entity.getPhoneNm().equals("")||entity.getPhoneNm()==null) {
                entity.setPhoneNm(userDto.getMobileNm());
            }if (entity.getReceiver().equals("")||entity.getReceiver()==null) {
                entity.setReceiver(userDto.getName());
            }


        BuyInsorder order = new BuyInsorder();
        BuyProductRes res = new BuyProductRes();

        order.setIuser(entity.getIuser());
        order.setPayment(entity.getPayment());
        order.setShipment(shipment);
        order.setPhoneNm(entity.getPhoneNm());
        order.setRequest(entity.getRequest());
        order.setReceiver(entity.getReceiver());
        order.setAddress(entity.getAddress());
        order.setAddressDetail(entity.getAddressDetail());
        order.setPoint(entity.getPoint());


        //제품의 수량이 0개 이하 일떄
        for (int i = 0; i <orderbasket.size(); i++) {
            BuySelquantityDto quantity = Mapper.quantity(orderbasket.get(i).getProductId());
            if (quantity.getQuantity() < 1){
                return null;
            }
        }

        int result = Mapper.InsBuy(order);

        if (result == 1){
            BuyUpdPointDto addpoint = new BuyUpdPointDto();
            BuyUpdPointDto updpoint = new BuyUpdPointDto();
            updpoint.setIuser(USERPK.getLoginUserPk());
            updpoint.setPoint(entity.getPoint());


            for (int i = 0; i <orderbasket.size(); i++) {

                //상품의 totalprice 가격 구하기
                 totalprice += orderbasket.get(i).getTotalprice();


                BuyDetailInsDto detaildto = new BuyDetailInsDto();
                detaildto.setOrderId(order.getOrderId());
                detaildto.setProductId(orderbasket.get(i).getProductId());
                detaildto.setCount(orderbasket.get(i).getCount());
                detaildto.setTotalPrice(orderbasket.get(i).getTotalprice());
                Mapper.InsBuyDetail(detaildto);

                BuyUpdDto updDto = new BuyUpdDto();
                updDto.setProductId(orderbasket.get(i).getProductId());
                updDto.setSaleVolumn(orderbasket.get(i).getCount());
                updDto.setQuantity(orderbasket.get(i).getCount());
                Mapper.updProduct(updDto);

                Mapper.delOrderbasket(orderbasket.get(i).getCartId());

            }


            res.setPoint(entity.getPoint());
            res.setOrderId(order.getOrderId());
            res.setTotalprice(totalprice);
            res.setPaymentprice(totalprice-entity.getPoint()); // 결제금액구하기
            int point = (int) (res.getPaymentprice() * earnedPercent);

            addpoint.setIuser(USERPK.getLoginUserPk());
            addpoint.setPoint(point);

            int removepoint = Mapper.removepoint(updpoint);



            if (removepoint!=1){
                throw new RuntimeException();
            }

            Mapper.addpoint(addpoint);

        }else
            throw new RuntimeException();

        return res;
    }

    public BuyProductRes BuyIdProduct(BuyInsIdDto dto){
        Long iuser = USERPK.getLoginUserPk();
        final int shipmnet = 1;
        final float earnedPercent = 0.03F;

        BuyInsorder order = new BuyInsorder();
        order.setIuser(iuser);
        order.setPayment(dto.getPayment());
        order.setShipment(shipmnet);
        order.setPhoneNm(dto.getPhoneNm());
        if (dto.getRequest().equals("")||dto.getRequest()==null) {
            order.setRequest("요청사항 없음");
        }else
            order.setRequest(dto.getRequest());
        order.setAddress(dto.getAddress());
        order.setAddressDetail(dto.getAddressDetail());
        order.setPoint(dto.getPoint());
        order.setReceiver(dto.getReceiver());

        //주문내역추가
        int result = Mapper.InsBuy(order);

        BuyUpdPointDto addpoint = new BuyUpdPointDto();
        BuyUpdPointDto updpoint = new BuyUpdPointDto();
        updpoint.setIuser(iuser);
        updpoint.setPoint(dto.getPoint());
        addpoint.setIuser(iuser);

        if (result == 1){

            BuyDetailInsDto detaildto = new BuyDetailInsDto();
            detaildto.setOrderId(order.getOrderId());
            detaildto.setProductId(dto.getProductId());
            detaildto.setCount(dto.getCount());
            detaildto.setTotalPrice(dto.getTotalprice());
            //주문상세내역 추가
            Mapper.InsBuyDetail(detaildto);

            BuyUpdDto updDto = new BuyUpdDto();
            updDto.setProductId(dto.getProductId());
            updDto.setSaleVolumn(dto.getCount());
            updDto.setQuantity(dto.getCount());
            //상품 개수 업데이트
            Mapper.updProduct(updDto);
        }

        //결제 포인트 계산

        dto.getPoint();
        int point = (int) (dto.getTotalprice() * earnedPercent);
        addpoint.setPoint(point);
        Mapper.removepoint(updpoint);
        Mapper.addpoint(addpoint);


        // 리턴값
        BuyProductRes res = new BuyProductRes();
        res.setPoint(dto.getPoint());
        res.setOrderId(order.getOrderId());
        res.setTotalprice(dto.getTotalprice());
        res.setPaymentprice(dto.getTotalprice() - dto.getPoint());

        return res;
    }

}
