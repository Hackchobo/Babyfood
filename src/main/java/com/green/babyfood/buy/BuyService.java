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

        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String format = formatter.format(date);


        log.info("formatter : {}",formatter.format(date));

        BuySelorderIdDto buySelorderIdDto = Mapper.selorderId();
        String selorderId = buySelorderIdDto.getOrderId();
        String substring = selorderId.substring(8,12);
        String str = format + substring;

        Long result = (long) Integer.parseInt(str);

        Long orderId2 = result +1;

        System.out.println(orderId2);


        List<BuySelOrderDto> product = Mapper.selorderproduct(orderId);
        BuySelUserDto user = Mapper.selorder(orderId);

        BuySelOrderlistDto build = BuySelOrderlistDto.builder().user(user).order(product).build();

        return build;
    }
}
