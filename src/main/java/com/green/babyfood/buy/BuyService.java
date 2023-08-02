package com.green.babyfood.buy;

import com.green.babyfood.buy.model.BuyDetailInsDto;
import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuyInsDto;
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

        int i = Mapper.InsBuy(dto);

        BuyDetailInsDto dto2 = new BuyDetailInsDto();

        dto2.setProductId(entity.getProductId());
        dto2.setCount(entity.getCount());
        dto2.setOrderId(dto.getOrderId());
        dto2.setTotalPrice(entity.getTotalPrice());

        int result = Mapper.InsBuyDetail(dto2);

        if (result ==1){
            Mapper.delOrderbasket(entity.getCartId());
        }


        return dto.getOrderId();
    }

}
