package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.substring;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyMapper Mapper;

    public Long BuyProduct(BuyEntity entity){

        final int shipment = 1;
        final float earnedPercent = 0.03F;

        // 결제할때 orderID 를 2023080400001

        BuyInsDto dto = new BuyInsDto();


        dto.setIuser(entity.getIuser());
        dto.setPayment(entity.getPayment());
        dto.setShipment(shipment);
        dto.setPhoneNm(entity.getPhoneNm());
        dto.setRequest(entity.getRequest());
        dto.setReceiver(entity.getReceiver());
        dto.setAddress(entity.getAddress());
        dto.setAddressDetail(entity.getAddressDetail());

        int result = Mapper.InsBuy(dto);

        if (result == 1){
            BuyPointUpdDto addpoint = new BuyPointUpdDto();
            BuyPointUpdDto updpoint = new BuyPointUpdDto();
            updpoint.setIuser(entity.getIuser());
            updpoint.setPoint(entity.getPoint());
            System.out.println(entity.getPoint());


            float point = 0;

            for (int i = 0; i <entity.getOrderbasket().size(); i++) {

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


                point += (entity.getOrderbasket().get(i).getTotalprice() * earnedPercent );

                System.out.println("point: " + point);
            }
            addpoint.setIuser(entity.getIuser());
            addpoint.setPoint(point);

            Mapper.addpoint(addpoint);
            Mapper.removepoint(updpoint);

        }else
            return 0L;

        return dto.getOrderId();
    }

    public BuyPoint point(Long iuser){
        return Mapper.point(iuser);
    }


}
