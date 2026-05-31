package com.emailApplication.service;




import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
public class MailService {

    private final JavaMailSender javaMailSender;


    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }
    @Async
    public void sendMail(String toEmail,String subject,String message){

        System.out.println("Before Sending Mail : " + Thread.currentThread().getName());

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8"
            );

            helper.setTo(toEmail);
            helper.setSubject(subject);


            helper.setText(message, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error sending HTML email", e);
        }








    }


}
