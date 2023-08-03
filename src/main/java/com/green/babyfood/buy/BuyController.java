package com.green.babyfood.buy;

import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuyEntity;
import com.green.babyfood.buy.model.BuySelOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buy")
public class BuyController {
    private final BuyService SERVICE;

    @PostMapping()
    public Long BuyProduct(BuyEntity entity){
        return SERVICE.BuyProduct(entity);

    }
    @GetMapping()
    public BuySelOrderDto GetBuyProduct(int orderId){
        return SERVICE.selorderproduct(orderId);
    }
}
