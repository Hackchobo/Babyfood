package com.green.babyfood.email;

import com.green.babyfood.email.model.MailReservation;
import com.green.babyfood.email.model.MailSendDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "메일발송")
@RestController
@EnableScheduling
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService service;


    public EmailController(EmailService service) {
        this.service = service;
    }
    private MailReservation reserveMail;


    @PostMapping("/send")
    @Operation(summary = "메일 발송기능",description = "사용법 <br>"+
            "mailAddress : 수신자 메일 주소<br>"+
            "title : 메일 제목<br>"+
            "ctnt : 내용 <br>")
    public void postSend(@RequestBody MailSendDto dto) {
        service.send(dto);
        // 보내고 싶은 메일이 있다면 dto 객체에 내용 맞춰서 넣은 후 해당 메소드 호출
    }


    @PostMapping("/send/Reservation")
    @Operation(summary = "주1회 자동으로 메일 발송을 위해 메일 내용 작성",description = "매주 월요일 오전10시 자동발송 <br>"+
            "title : 메일 제목<br>"+
            "ctnt : 내용 <br>")
    public void reservationMailPost(@RequestBody MailReservation dto){
        reserveMail = dto;
    }

    //@Scheduled(cron = "1 0 15 ? * ?")
    @Scheduled(cron = "0 6 * * * *") // 테스트용, 모든 시간의 n분마다 발송
    @PostMapping("/send/Reservation/mail") // 주 1회 자동발송 메일
    @Operation(summary = "주1회 자동으로 메일 발송")
    public void cycleMail(){
        service.cycleMail(reserveMail); // 미리 저장해둔 예약내용으로 발송
    }

}
