package com.green.babyfood.email;
import com.green.babyfood.email.model.MailReservation;
import com.green.babyfood.email.model.MailSendDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Service
@EnableScheduling
public class EmailService {


    private static String user = "green502teamA@gmail.com";
    private static String password = "qvoksygqngguntbl";
    // 앱2차비밀번호 !!! 비밀번호 넣으면 동작하지 않습니다
    // 팀 공동사용중, 비밀번호나 세팅 확인 필요시 슬랙참고해주세요


    public void send(MailSendDto dto) {

        log.info("메일 전송 시작");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(user, "babyfoodTest")); // 발신메일, 발신자이름
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dto.getMailAddress())); // 수신자 메일 주소
            message.setSubject(dto.getTitle()); //메일 제목을 입력
            message.setText(dto.getCtnt());
            Transport.send(message);
            log.info("메일 전송 완료");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void cycleMail(MailReservation dto) {
        log.info("주1회 월요일 오전 10시 메일 자동 발송");
        MailSendDto dto1 = new MailSendDto();
        dto1.setCtnt("111");
        dto1.setMailAddress(dto.getMailAddress());
        dto1.setTitle(dto.getTitle());
        send(dto1); // 메일 발송 메소드 호출
        log.info("정기메일 발송 완료");
    }
}
