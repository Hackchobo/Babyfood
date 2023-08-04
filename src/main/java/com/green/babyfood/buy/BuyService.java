package com.green.babyfood.buy;

import com.green.babyfood.buy.model.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        dto.setCallUser(entity.getCalluser());
        dto.setRequest(entity.getRequest());
        dto.setReceiver(entity.getReceiver());
        dto.setAddress(entity.getAddress());
        dto.setAddress(entity.getAddressDetail());
        int result = Mapper.InsBuy(dto);

        if (result == 1){
            BuyPointUpdDto addpoint = new BuyPointUpdDto();
            BuyPointUpdDto remove = new BuyPointUpdDto();
            remove.setIuser(entity.getIuser());
            remove.setPoint(entity.getPoint());
            int point = 0;

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


                point += (entity.getOrderbasket().get(i).getTotalprice() / 5 );
            }
            addpoint.setIuser(entity.getIuser());
            addpoint.setPoint(point);

            Mapper.addpoint(remove);
            Mapper.removepoint(addpoint);

        }else
            return 0L;

        return dto.getOrderId();
    }

    public BuySelOrderlistDto selorderproduct(int orderId){
        List<BuySelOrderDto> product = Mapper.selorderproduct(orderId);
        BuySelUserDto user = Mapper.selorder(orderId);

        BuySelOrderlistDto build = BuySelOrderlistDto.builder().user(user).order(product).build();

        return build;
    }
}
