package com.emailApplication.service;




import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;


    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    public void sendMail(String toEmail,String subject,String message){

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(toEmail);
        mail.setSubject(subject);
        mail.setText(message);

        javaMailSender.send(mail);


    }


}
