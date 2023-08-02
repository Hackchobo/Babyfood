package com.green.babyfood.email;
import com.green.babyfood.email.model.MailCycleDto;
import com.green.babyfood.email.model.MailSendDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Slf4j
@Service
public class EmailService {


    private static String user = "kdy12061004@gmail.com";
    private static String password = "poigxflxgjinjqtt"; // 앱2차비밀번호 !!! 본인 비번 바로넣지마세요 !!!


    public static void send(MailSendDto dto) {

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

    public void cycleMail(MailCycleDto dto) {
        log.info("주1회 월요일 오전 10시 메일 자동 발송");


        log.info("예약메일 발송 완료");
    }
}
