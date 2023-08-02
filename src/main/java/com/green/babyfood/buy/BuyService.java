package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyMapper Mapper;

    public Long BuyProduct(BuyEntity entity){
        BuyInsDto dto = new BuyInsDto();

        dto.setIuser(entity.getIuser());
        dto.setPayment(entity.getPayment());
        dto.setShipment(1);
        dto.setCall_user(entity.getCalluser());
        dto.setRequest(entity.getRequest());
        dto.setReciever(entity.getReciever());

        Mapper.InsBuy(dto);

        BuyDetailInsDto detailInsDto = new BuyDetailInsDto();


        detailInsDto.setProductId(entity.getProductId());
        detailInsDto.setCount(entity.getCount());
        detailInsDto.setOrderId(dto.getOrderId());
        detailInsDto.setTotalPrice(entity.getTotalPrice());

        BuyUpdDto updDto = new BuyUpdDto();

        updDto.setQuantity(entity.getCount());
        updDto.setSaleVolumn(entity.getCount());
        updDto.setProductId(entity.getProductId());

        BuyPointUpdDto pointdto = new BuyPointUpdDto();

        pointdto.setIuser(entity.getIuser());
        pointdto.setPoint(entity.getPoint());

        int result = Mapper.InsBuyDetail(detailInsDto);

        if (result ==1){

            Mapper.updProduct(updDto);
            Mapper.delOrderbasket(entity.getCartId());
            Mapper.userpoint(pointdto);

        }

        return dto.getOrderId();
    }

}
