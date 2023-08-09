package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.substring;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyMapper Mapper;

    public BuyProductRes BuyProduct(BuyEntity entity){

        final int shipment = 1;
        final float earnedPercent = 0.03F;
        int totalprice = 0;

        // 값이 없을때
        for (int i = 0; i <entity.getOrderbasket().size(); i++) {
            if (entity.getRequest().equals("")){
                entity.setRequest("요청사항 없음");
            }

        }

        // 결제할때 orderID 를 2023080400001


        BuyInsDto dto = new BuyInsDto();
        BuyProductRes res = new BuyProductRes();

        dto.setIuser(entity.getIuser());
        dto.setPayment(entity.getPayment());
        dto.setShipment(shipment);
        dto.setPhoneNm(entity.getPhoneNm());
        dto.setRequest(entity.getRequest());
        dto.setReceiver(entity.getReceiver());
        dto.setAddress(entity.getAddress());
        dto.setAddressDetail(entity.getAddressDetail());


        //제품의 수량이 0개이하이면 return 0
        for (int i = 0; i <entity.getOrderbasket().size(); i++) {
            BuySelquantityDto quantity = Mapper.quantity(entity.getOrderbasket().get(i).getProductId());
            if (quantity.getQuantity() < 1){
                return null;
            }
        }

            Mapper.InsBuy(dto);

        int result = Mapper.InsBuy(dto);

        if (result == 1){
            BuyUpdPointDto addpoint = new BuyUpdPointDto();
            BuyUpdPointDto updpoint = new BuyUpdPointDto();
            updpoint.setIuser(entity.getIuser());
            updpoint.setPoint(entity.getPoint());


            for (int i = 0; i <entity.getOrderbasket().size(); i++) {

                //상품의 totalprice 가격 구하기
                 totalprice += entity.getOrderbasket().get(i).getTotalprice();


                BuyDetailInsDto detaildto = new BuyDetailInsDto();
                detaildto.setOrderId(dto.getOrderId());
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
            res.setOrderId(dto.getOrderId());
            res.setTotalprice(totalprice);
            res.setPaymentprice(totalprice-entity.getPoint()); // 결제금액구하기
            int point = (int) (res.getPaymentprice() * earnedPercent);

            addpoint.setIuser(entity.getIuser());
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
