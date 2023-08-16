package com.green.babyfood.main;

import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.orderbasket.OrderBasketMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MainMapperTest {

    @Autowired
    private MainMapper mapper;

    @Test
    void mainSelView(){


        List<MainSelVo> list = mapper.mainSelView(0, 10);
        assertEquals(list.get(0).getProductId(),1);
//        assertEquals(list.get(0).getTitle(),"string");
        assertEquals(list.size(),10);




    }

}