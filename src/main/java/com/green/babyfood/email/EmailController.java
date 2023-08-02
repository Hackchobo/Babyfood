package com.green.babyfood.email;

import com.green.babyfood.email.model.MailCycleDto;
import com.green.babyfood.email.model.MailSendDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "메일발송")
@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }


    @PostMapping("/send")
    @Operation(summary = "메일 발송기능",description = "사용법 <br>"+
            "mailAddress : 수신자 메일 주소<br>"+
            "title : 메일 제목<br>"+
            "ctnt : 내용 <br>")
    public void postSend(@RequestBody MailSendDto dto) {
        service.send(dto);
        // 보내고 싶은 메일이 있다면 dto 객체에 내용 맞춰서 넣은 후 해당 메소드 호출
    }


    @PostMapping("/sendcyclemail") // 주 1회 자동발송 메일
    @Operation(summary = "주1회 추천상품 자동으로 메일 발송",description = ""+
            "title : 메일 제목<br>"+
            "ctnt : 내용 <br>")
    public void cycleMail(MailCycleDto dto){
        service.cycleMail(dto);
    }

}
