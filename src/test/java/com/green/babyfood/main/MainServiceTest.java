package com.green.babyfood.main;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.main.model.MainSelVo;
import com.green.babyfood.main.model.MainSelVoMaxPaige;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import({MainService.class, AuthenticationFacade.class})
class MainServiceTest {

    @MockBean
    private MainMapper mapper;
    @Autowired
    private MainService service;
    @MockBean
    private AuthenticationFacade USERPK;

    @Test
    void mainSelView(){
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);
        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);
        when(mapper.mainSelView(anyInt(),anyInt())).thenReturn(list);
        MainSelVoMaxPaige mainSel1 = service.mainSelView(anyInt(), anyInt());


        MainSelVoMaxPaige mainSel2=new MainSelVoMaxPaige();
        mainSel2.setMaxPage(10);
        mainSel2.setList(list);

       assertEquals(mainSel1.getList().get(0).getProductId(),mainSel2.getList().get(0).getProductId());
       assertEquals(mainSel1.getList().get(0).getPrice(),mainSel2.getList().get(0).getPrice());
       assertEquals(mainSel1.getList().get(0).getQuantity(),mainSel2.getList().get(0).getQuantity());
       assertEquals(mainSel1.getList().get(0).getThumbnail(),mainSel2.getList().get(0).getThumbnail());
       assertEquals(mainSel1.getList().get(0).getVolumn(),mainSel2.getList().get(0).getVolumn());
       assertEquals(mainSel1.getList().get(0).getName(),mainSel2.getList().get(0).getName());
       assertEquals(mainSel1.getList().get(0).getTitle(),mainSel2.getList().get(0).getTitle());

       verify(mapper).mainSelView(anyInt(),anyInt());
       //verify(mapper).maxPaige();
    }

    @Test
    void bestSell(){
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);
        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);
        when(mapper.bestSell()).thenReturn(list);
        List<MainSelVo> list1 = service.bestSell();

        assertEquals(list.get(0).getProductId(),list1.get(0).getProductId());
        assertEquals(list.get(0).getThumbnail(),list1.get(0).getThumbnail());
        assertEquals(list.get(0).getPrice(),list1.get(0).getPrice());
        assertEquals(list.get(0).getName(),list1.get(0).getName());
        assertEquals(list.get(0).getVolumn(),list1.get(0).getVolumn());
        assertEquals(list.get(0).getQuantity(),list1.get(0).getQuantity());
        assertEquals(list.get(0).getTitle(),list1.get(0).getTitle());

        verify(mapper).bestSell();
    }

    @Test
    void bestSellAll(){
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);
        List<MainSelVo> list=new ArrayList<>();
        list.add(mainSelVo1);


        when(mapper.bestSellAllMaxPage()).thenReturn(1);
        int result=mapper.bestSellAllMaxPage();
        when(mapper.bestSellAll(0,20)).thenReturn(list);


        MainSelVoMaxPaige mainSel1 = service.bestSellAll(1,20);

        MainSelVoMaxPaige mainSel2=new MainSelVoMaxPaige();
        mainSel2.setMaxPage(10);
        mainSel2.setList(list);

        assertEquals(mainSel1.getList().get(0).getProductId(),mainSel2.getList().get(0).getProductId());
        assertEquals(mainSel1.getList().get(0).getThumbnail(),mainSel2.getList().get(0).getThumbnail());
        assertEquals(mainSel1.getList().get(0).getName(),mainSel2.getList().get(0).getName());
        assertEquals(mainSel1.getList().get(0).getPrice(),mainSel2.getList().get(0).getPrice());
        assertEquals(mainSel1.getList().get(0).getQuantity(),mainSel2.getList().get(0).getQuantity());
        assertEquals(mainSel1.getList().get(0).getTitle(),mainSel2.getList().get(0).getTitle());
        assertEquals(mainSel1.getList().get(0).getVolumn(),mainSel2.getList().get(0).getVolumn());
    }

    @Test
    void birthRecommendFilter(){
        MainSelVo mainSelVo1=new MainSelVo();
        mainSelVo1.setProductId(1L);
        mainSelVo1.setTitle("테스트1");
        mainSelVo1.setName("네임테스트1");
        mainSelVo1.setPrice(1000);
        mainSelVo1.setQuantity(10);
        mainSelVo1.setThumbnail("main.pic1");
        mainSelVo1.setVolumn(100);
        List<MainSelVo> list1=new ArrayList<>();
        list1.add(mainSelVo1);

        when(mapper.birth(anyLong())).thenReturn(5);
        when(mapper.birthRecommendFilter(anyInt(),anyInt())).thenReturn(list1);
        List<MainSelVo> list2 = service.birthRecommendFilter(20);

        assertEquals(list1.get(0).getProductId(),list2.get(0).getProductId());
        assertEquals(list1.get(0).getName(),list2.get(0).getName());
        assertEquals(list1.get(0).getThumbnail(),list2.get(0).getThumbnail());
        assertEquals(list1.get(0).getPrice(),list2.get(0).getPrice());
        assertEquals(list1.get(0).getTitle(),list2.get(0).getTitle());
        assertEquals(list1.get(0).getVolumn(),list2.get(0).getVolumn());
        assertEquals(list1.get(0).getQuantity(),list2.get(0).getQuantity());

        verify(mapper).birth(anyLong());
        verify(mapper).birthRecommendFilter(anyInt(),anyInt());

    }


}