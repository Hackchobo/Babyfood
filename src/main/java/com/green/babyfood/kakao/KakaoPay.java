package com.green.babyfood.kakao;


import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.kakao.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class KakaoPay {


    private final KakaoMapper mapper;
    private static final String HOST = "https://kapi.kakao.com";

    private  KakaoPayReadyVO kakaoPayReadyVO;
    private  KakaoPayApprovalVO kakaoPayApprovalVO;

    private final AuthenticationFacade USERPK;
    private List<KakaoVo> list=new ArrayList();

    private int check=0;
    private KakaoVo kakaoVoNow=new KakaoVo();
    private List<KakaoVo> kakaoVoCart=new ArrayList<>();

    private KakaopayDto dto10=new KakaopayDto();



    public String kakaoPayReady(KakaopayDto dto) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "0d385e1ad926e7780e84cdfd46bc0129");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");



        //상품 정보를 받아온다
        String itemNamePlus="";
        int sum=0;
        dto10=dto;
        dto.setSum(sum);
        if(dto.getProductId()!=0){      //바로구매
            check=1;
            KakaoVo kakaoVo = mapper.insProductId(dto.getProductId());
            kakaoVoNow=kakaoVo;


            itemNamePlus=kakaoVo.getItemName();
            sum=Integer.parseInt(kakaoVo.getPrice())*dto.getCount();
        }
        else if(dto.getProductId()==0){ //장바구니로 구매
            check=0;
            List<KakaoVo> selProductInfo = mapper.ins(USERPK.getLoginUserPk());
            kakaoVoCart=selProductInfo;


            list=selProductInfo;
            itemNamePlus=(selProductInfo.get(0).getItemName())+" 외,"+(selProductInfo.size()-1)+"개";
            for (int i = 0; i < list.size(); i++) {
                sum+=Integer.parseInt(list.get(i).getPrice());
            }
        }

        //바로구매
        if(check==1){
            KakaoPaySuccessDto dto1=new KakaoPaySuccessDto();
            dto1.setIuser(USERPK.getLoginUserPk());
            dto1.setPoint(dto10.getPoint());
            dto1.setPayment(dto10.getPayment());
            dto1.setAddress(dto10.getAddress());
            dto1.setPhoneNm(dto10.getPhoneNm());
            dto1.setRequest(dto10.getRequest());
            dto1.setReciever(dto10.getReciever());
            dto1.setProductId(dto10.getProductId());
            dto1.setAddressDetail(dto10.getAddressDetail());
            dto1.setCount(dto10.getCount());
            dto1.setTotalPrice(sum);
            System.out.println(dto10.getSum());
            mapper.insOrderList(dto1);
            mapper.insOrderDetail(dto1);
        }
        //장바구니 구매
        else if(check==0){
            for (int i = 0; i < kakaoVoCart.size(); i++) {
                int sum1=Integer.parseInt(kakaoVoCart.get(i).getPrice())*Integer.parseInt(kakaoVoCart.get(i).getCount());
                KakaoPaySuccessListDto dto1 = new KakaoPaySuccessListDto();
                dto1.setIuser(USERPK.getLoginUserPk());
                dto1.setPoint(dto10.getPoint());
                dto1.setPayment(dto10.getPayment());
                dto1.setAddress(dto10.getAddress());
                dto1.setPhoneNm(dto10.getPhoneNm());
                dto1.setRequest(dto10.getRequest());
                dto1.setReciever(dto10.getReciever());
                dto1.setProductId(kakaoVoCart.get(i).getProductId());
                dto1.setAddressDetail(dto10.getAddressDetail());
                dto1.setCount(Integer.valueOf(kakaoVoCart.get(i).getCount()));
                dto1.setTotalPrice(sum);
                mapper.insOrderListBasket(dto1);
                mapper.insOrderDetailBasket(dto1);
            }
        }



        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", USERPK.getLoginUserPk());
        params.add("item_name", itemNamePlus);
        params.add("quantity", String.valueOf(list.size()));
        params.add("total_amount", String.valueOf(sum));
        params.add("tax_free_amount", "100");
        params.add("approved_at", LocalDateTime.now().toString());
        params.add("approval_url", "http://192.168.0.144:5001/kakaoPay");
        params.add("cancel_url", "http://192.168.0.144:5001/kakaoPayCancel");
        params.add("fail_url", "http://192.168.0.144:5001/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url(); //카카오페이 return

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";

    }


    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "0d385e1ad926e7780e84cdfd46bc0129");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");



        //바로구매
        if(check==1){
            KakaoPaySuccessDto dto=new KakaoPaySuccessDto();
            dto.setIuser(USERPK.getLoginUserPk());
            dto.setPoint(dto10.getPoint());
            dto.setPayment(dto10.getPayment());
            dto.setAddress(dto10.getAddress());
            dto.setPhoneNm(dto10.getPhoneNm());
            dto.setRequest(dto10.getRequest());
            dto.setReciever(dto10.getReciever());
            dto.setProductId(dto10.getProductId());
            dto.setAddressDetail(dto10.getAddressDetail());
            dto.setCount(dto10.getCount());
            dto.setTotalPrice(dto10.getSum());
            System.out.println(dto10.getSum());
            mapper.insOrderList(dto);
            mapper.insOrderDetail(dto);
        }
         //장바구니 구매
        else if(check==0){
            for (int i = 0; i < kakaoVoCart.size(); i++) {
                int sum=Integer.parseInt(kakaoVoCart.get(i).getPrice())*Integer.parseInt(kakaoVoCart.get(i).getCount());
                KakaoPaySuccessListDto dto = new KakaoPaySuccessListDto();
                dto.setIuser(USERPK.getLoginUserPk());
                dto.setPoint(dto10.getPoint());
                dto.setPayment(dto10.getPayment());
                dto.setAddress(dto10.getAddress());
                dto.setPhoneNm(dto10.getPhoneNm());
                dto.setRequest(dto10.getRequest());
                dto.setReciever(dto10.getReciever());
                dto.setProductId(kakaoVoCart.get(i).getProductId());
                dto.setAddressDetail(dto10.getAddressDetail());
                dto.setCount(Integer.valueOf(kakaoVoCart.get(i).getCount()));
                dto.setTotalPrice(dto10.getSum());
                mapper.insOrderListBasket(dto);
                mapper.insOrderDetailBasket(dto);
            }
        }



        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", USERPK.getLoginUserPk());
        params.add("pg_token", pg_token);
        params.add("total_amount", "2100");
        params.add("approved_at", LocalDateTime.now().toString());
        log.info(pg_token);
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);


        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
