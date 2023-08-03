package com.green.babyfood.email.model;

import lombok.Data;

@Data
public class MailReservation {
    private String title; // 제목
    private String mailAddress; // 수신자 메일주소
    private String ctnt; // 내용
}
