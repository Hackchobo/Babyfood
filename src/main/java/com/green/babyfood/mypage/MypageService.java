package com.green.babyfood.mypage;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.config.security.PasswordEncoderConfiguration;
import com.green.babyfood.mypage.model.*;
import com.green.babyfood.user.model.CreatePicDto;
import com.green.babyfood.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MypageService {

    @Value("${file.dir}")
    private String fileDir;

    private final MypageMapper mapper;
    private final PasswordEncoder PW_ENCODER;
    private final AuthenticationFacade USERPK;

    public  OrderlistSelDto[] Orderlist(OrderlistMonthsSelDto dto){
        //dto.setIuser(USERPK.getLoginUserPk());

        List<OrderlistCountSelDto> orderlist = mapper.orderlist(dto);

        OrderlistSelDto[] orderlistSelDto = new OrderlistSelDto[orderlist.size()];


        for (int i = 0; i < orderlist.size(); i++) {

            if (orderlist.get(i).getShipment().equals("1")){
                orderlist.get(i).setShipment("상품 준비중");
            } else if (orderlist.get(i).getShipment().equals("2")) {
                orderlist.get(i).setShipment("상품 배송중");
            } else if (orderlist.get(i).getShipment().equals("3")) {
                orderlist.get(i).setShipment("상품 주문취소");
            } else if (orderlist.get(i).getShipment().equals("4")) {
                orderlist.get(i).setShipment("상품 배송완료");
            }

            if (orderlist.get(i).getCount()>0){
                String name = orderlist.get(i).getName();
                StringBuffer sb = new StringBuffer();
                sb.append(name).append(" 외").append(orderlist.get(i).getCount()).append("개");

                String ordername = String.valueOf(sb);
                orderlist.get(i).setName(ordername);
            }
        }

        for (int i = 0; i < orderlistSelDto.length; i++) {
            orderlistSelDto[i]=new OrderlistSelDto();
            orderlistSelDto[i].setOrderId(orderlist.get(i).getOrderId());
            orderlistSelDto[i].setCreatedAt(orderlist.get(i).getCreatedAt());
            String path = "192.168.0.144:5001/img/webeditor/"+dto.getIuser()+"/"+orderlist.get(i).getThumbnail();
            orderlistSelDto[i].setThumbnail(path);
            orderlistSelDto[i].setName(orderlist.get(i).getName());
            orderlistSelDto[i].setPrice(orderlist.get(i).getPrice());
            orderlistSelDto[i].setShipment(orderlist.get(i).getShipment());
        }

        return orderlistSelDto;
    }

    public OrderlistSelUserDto OrderlistDetail(Long orderId){

        List<OrderlistDetailSelDto> orderlist = mapper.orderlistDetail(orderId);
        OrderlistUserDto user = mapper.selUser(orderId);

        for (int i = 0; i <orderlist.size(); i++) {

            String thumbnail = orderlist.get(i).getThumbnail();
            String path = "http://192.168.0.144:5001/img/webeditor/"+orderlist.get(i).getIuser()+"/"+thumbnail;
            orderlist.get(i).setThumbnail(path);

        }
        OrderlistSelUserDto build = OrderlistSelUserDto.builder().orderlist(orderlist).user(user).build();

        return build;
    }
    public int delorder(Long orderId){
         return mapper.delorder(orderId);
    }

    public ProfileSelDto profile(Long iuser){
        //iuser = USERPK.getLoginUserPk();
        ProfileSelDto profile = mapper.profile(iuser);

        String path = "http://192.168.0.144:5001/img/user/"+iuser+"/"+profile.getImage();
        profile.setImage(path);
        return profile;
    }

    public int UpdProfileDto(ProfileUpdDto dto){


        String encode = PW_ENCODER.encode(dto.getPassword());
        dto.setPassword(encode);

        return mapper.Updprofile(dto);
    }

    public int nicknmcheck(String nickname){
        String Nicknm = mapper.SelNickNm(nickname);
        if (nickname.equals(Nicknm)){
            return 1;
        }else
            return 0;
    }

    public int delUser(Long iuser){
       // iuser = USERPK.getLoginUserPk();
        return mapper.delUser(iuser);
    }


    public int updPicUser(MultipartFile pic, Long iuser){
        //iuser = USERPK.getLoginUserPk();
        String centerPath = String.format("%s/user/%d", FileUtils.getAbsolutePath(fileDir),iuser);


        File dic = new File(centerPath);
        if(!dic.exists()){
            dic.mkdirs();
        }

        String originFileName = pic.getOriginalFilename();
        String savedFileName = FileUtils.makeRandomFileNm(originFileName);
        String savedFilePath = String.format("%s/%s",centerPath, savedFileName);

        File target = new File(savedFilePath);
        try {
            pic.transferTo(target);
        }catch (Exception e) {
            return 0;
        }
        String img = savedFileName;
        try {
            int result = mapper.patchProfile(img,iuser);
            if(result == 0) {
                throw new Exception("프로필 사진을 등록할 수 없습니다.");
            }
        } catch (Exception e) {
            //파일 삭제
            target.delete();
            return 0;
        }
        return 1;
    }
}
