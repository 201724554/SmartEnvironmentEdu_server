package com.example.demo.mail.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("rusttutorial@gmail.com")
    private String serverEmail;

    public void sendAuthMail(String email, String authNumber)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(serverEmail);
        mailMessage.setTo(email);
        mailMessage.setText("인증번호는 " + authNumber + "입니다");
        javaMailSender.send(mailMessage);
    }
}
