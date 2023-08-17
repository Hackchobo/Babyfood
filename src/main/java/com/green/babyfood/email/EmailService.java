package com.green.babyfood.email;
import com.green.babyfood.email.model.MailReservation;
import com.green.babyfood.email.model.MailSendDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Service
@EnableScheduling
public class EmailService {


    private final String user = "green502teamA@gmail.com";
    private final String password = "kqvrkfnjbemoclmb";
    private final String admin = "dlwlsrb0307@naver.com";

    @Autowired
    private final EmailMapper mapper;

    public EmailService(EmailMapper mapper) {
        this.mapper = mapper;
    }
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
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(admin, "안녕하세요"));
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

//    public void cycleMail(MailReservation dto) {
//        log.info("전체 회원 대상 발송 정기메일 : 주1회 월요일 오전 10시 메일 자동 발송");
//
//        List<String> mailAddresses = Arrays.asList(dto.getMailAddress().toString());
//
//        for (String mailAddress : mailAddresses) {
//            sendEmail(dto, mailAddress);
//        }
//
//        log.info("정기메일 발송 완료");
//    }

//    public void sendEmail(MailReservation mailReservationDto, String recipient){
//        String title = mailReservationDto.getTitle();
//        String ctnt = mailReservationDto.getCtnt();
//
//        // JavaMail 설정
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.example.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(user, password);
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(user, "babyfoodTest")); // 발신메일, 발신자이름
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); // 수신자 메일 주소
//            message.setSubject(title);
//            message.setText(ctnt);
//            Transport.send(message);
//            log.info("메일 전송 완료");
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

}
