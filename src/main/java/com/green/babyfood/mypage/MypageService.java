package com.green.babyfood.mypage;

import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.mypage.model.*;
import com.green.babyfood.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public  OrderlistSelDto[] Orderlist(int month){
        OrderlistMonthsSelDto dto = new OrderlistMonthsSelDto();
        dto.setIuser(USERPK.getLoginUserPk());
        dto.setMonth(month);

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
            int count = mapper.count(orderlist.get(i).getOrderId());

            if (count>1){
                String name = orderlist.get(i).getName();
                StringBuffer sb = new StringBuffer();
                sb.append(name).append(" 외").append(count-1).append("개");

                String ordername = String.valueOf(sb);
                orderlist.get(i).setName(ordername);
            }
        }

        for (int i = 0; i < orderlistSelDto.length; i++) {
            orderlistSelDto[i]=new OrderlistSelDto();
            orderlistSelDto[i].setOrderId(orderlist.get(i).getOrderId());
            orderlistSelDto[i].setCreatedAt(orderlist.get(i).getCreatedAt());
            String path = "192.168.0.144:5001/img/webeditor/"+orderlist.get(i).getOrderId()+"/"+orderlist.get(i).getThumbnail();
            orderlistSelDto[i].setThumbnail(path);
            orderlistSelDto[i].setName(orderlist.get(i).getName());
            orderlistSelDto[i].setPrice(orderlist.get(i).getPrice());
            orderlistSelDto[i].setShipment(orderlist.get(i).getShipment());
        }

        return orderlistSelDto;
    }

    public OrderlistSelUserDto OrderlistDetail(Long orderId){
        OrderIuserDto dto = new OrderIuserDto();

        List<OrderlistDetailSelDto> orderlist = mapper.orderlistDetail(orderId);
        OrderlistUserDto user = mapper.selUser(orderId);

        for (int i = 0; i <orderlist.size(); i++) {

            String thumbnail = orderlist.get(i).getThumbnail();
            String path = "http://192.168.0.144:5001/img/webeditor/"+orderlist.get(i).getProductId()+"/"+thumbnail;
            orderlist.get(i).setThumbnail(path);

        }
        OrderlistSelUserDto selUserDto = new OrderlistSelUserDto();
        selUserDto.setOrderlist(orderlist);
        selUserDto.setUser(user);

        return selUserDto;
    }
    public int delorder(Long orderId){
         return mapper.delorder(orderId);
    }

    public ProfileSelDto profile(){
        OrderIuserDto dto = new OrderIuserDto();
        dto.setIuser(USERPK.getLoginUserPk());
        ProfileSelDto profile = mapper.profile(dto);

        String path = "http://192.168.0.144:5001/img/user/"+dto.getIuser()+"/"+profile.getImage();
        profile.setImage(path);
        return profile;
    }

    public int UpdProfileDto(ProfileUpdDto dto){
        Long iuser = USERPK.getLoginUserPk();
        ProfileEntity entity = new ProfileEntity();

        String nickNm = mapper.SelNickNm(dto.getNickNm());

        entity.setIuser(iuser);
        if (!dto.getNickNm().equals(nickNm) ){
            entity.setNickNm(dto.getNickNm());
        }

        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthday(dto.getBirthday());
        entity.setZipcode(dto.getZipcode());
        entity.setAddress(dto.getAddress());
        entity.setAddressDetail(dto.getAddressDetail());

        if (!dto.getPassword().equals("")){
            String encode = PW_ENCODER.encode(dto.getPassword());
            entity.setPassword(encode);
        }

        return mapper.Updprofile(entity);
    }

    public int nicknmcheck(String nickname){
        String Nicknm = mapper.SelNickNm(nickname);
        if (nickname.equals(Nicknm)){
            return 1;
        }else
            return 0;
    }

    public int delUser(){
        OrderIuserDto dto = new OrderIuserDto();
       dto.setIuser(USERPK.getLoginUserPk());
        return mapper.delUser(dto);
    }


    public int updPicUser(MultipartFile pic){
        Long iuser = USERPK.getLoginUserPk();
        ProfileUpdPicDto dto = new ProfileUpdPicDto();
        dto.setIuser(iuser);
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
        dto.setImg(img);
        try {
            int result = mapper.patchProfile(dto);
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
