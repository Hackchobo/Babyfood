package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import com.green.babyfood.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        order.setIuser(USERPK.getLoginUserPk());
        order.setPayment(entity.getPayment());
        order.setShipment(shipment);
        order.setPhoneNm(entity.getPhoneNm());
        order.setRequest(entity.getRequest());
        order.setReceiver(entity.getReceiver());
        order.setAddress(entity.getAddress());
        order.setAddressDetail(entity.getAddressDetail());


        //제품의 수량이 0개이하이면 return 0
        for (int i = 0; i <entity.getOrderbasket().size(); i++) {
            BuySelquantityDto quantity = Mapper.quantity(entity.getOrderbasket().get(i).getProductId());
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


            for (int i = 0; i <entity.getOrderbasket().size(); i++) {

                //상품의 totalprice 가격 구하기
                 totalprice += entity.getOrderbasket().get(i).getTotalprice();


                BuyDetailInsDto detaildto = new BuyDetailInsDto();
                detaildto.setOrderId(order.getOrderId());
                detaildto.setProductId(entity.getOrderbasket().get(i).getProductId());
                detaildto.setCount(entity.getOrderbasket().get(i).getCount());
                detaildto.setTotalPrice(entity.getOrderbasket().get(i).getTotalprice());
                Mapper.InsBuyDetail(detaildto);

                BuyUpdDto updDto = new BuyUpdDto();
                updDto.setProductId(entity.getOrderbasket().get(i).getProductId());
                updDto.setSaleVolumn(entity.getOrderbasket().get(i).getCount());
                updDto.setQuantity(entity.getOrderbasket().get(i).getCount());
                Mapper.updProduct(updDto);

                Mapper.delOrderbasket(entity.getOrderbasket().get(i).getCartId());

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

}
