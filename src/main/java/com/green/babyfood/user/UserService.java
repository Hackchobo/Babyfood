package com.green.babyfood.user;

import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.AuthenticationFacade;
import com.green.babyfood.config.security.JwtTokenProvider;
import com.green.babyfood.sign.SignMapper;
import com.green.babyfood.sign.SignService;
import com.green.babyfood.user.model.*;
import com.green.babyfood.util.FileUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${file.dir}")
    private String fileDir;

    private final UserMapper mapper;
    private final SignService service;
    private final PasswordEncoder PW_ENCODER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final RedisService REDIS_SERVICE;
    private final AuthenticationFacade FACADE;

    /*@Autowired
    public UserService(UserMapper mapper,@Value("${file.dir}")String fileDir) {
        this.mapper = mapper;
        this.fileDir=fileDir;
    }*/

    /*public int insUser(UserInsDto dto){
        if (!dto.getPassword().equals(dto.getSecret())){
            return -1;
        }
        return mapper.insUser(dto);
    }

    public int insAdmin(AdminInsDto dto){
        return mapper.insAdmin(dto);
    }*/

    public List<AdminUserEntity> selUser(UserSelEntity entity){
        int ROW_PER_PAGE = entity.getRow();
        int startIdx = (entity.getPage() - 1) * ROW_PER_PAGE;
        entity.setRowLen(ROW_PER_PAGE);
        entity.setStartIdx(startIdx);
        return mapper.selUser(entity);
    }

    public int updUser(AdminUserUpdDto dto){
        return mapper.updUser(dto);
    }

    public int updPicUser(MultipartFile pic, CreatePicDto dto){
        String centerPath = String.format("%s/user/%d", FileUtils.getAbsolutePath(fileDir),dto.getEmail());

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
        dto.setImage(savedFileName);
        try {
            int result = mapper.updPicUser(dto);
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

    public int updPointUser(UserPointDto dto){
        return mapper.updPointUser(dto);
    }

    public int delUser(UserDelDto dto, HttpServletRequest req){
        service.logout(req);
        return mapper.delUser(dto);

    }





}
