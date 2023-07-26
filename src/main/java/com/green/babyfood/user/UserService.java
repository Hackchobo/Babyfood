package com.green.babyfood.user;

import com.green.babyfood.user.model.*;
import com.green.babyfood.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    @Value("${file.dir}")
    private String fileDir;
    @Autowired
    private final UserMapper mapper;

    /*@Autowired
    public UserService(UserMapper mapper,@Value("${file.dir}")String fileDir) {
        this.mapper = mapper;
        this.fileDir=fileDir;
    }*/

    public int insUser(UserInsDto dto){
        if (!dto.getPassword().equals(dto.getSecret())){
            return -1;
        }
        return mapper.insUser(dto);
    }

    public int insAdmin(AdminInsDto dto){
        return mapper.insAdmin(dto);
    }

    public List<UserEntity> selUser(){
        return mapper.selUser();
    }

    public int updUser(UserUpdDto dto){
        return mapper.updUser(dto);
    }

    public int updPicUser(MultipartFile pic, CreatePicDto dto){
        String centerPath = String.format("%s/user/%d", FileUtils.getAbsolutePath(fileDir),dto.getIuser());

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

    public int delUser(UserDelDto dto){
        return mapper.delUser(dto);
    }
}
