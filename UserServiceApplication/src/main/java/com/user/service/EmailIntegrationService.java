package com.user.service;

import com.user.client.EmailClient;
import com.user.client.EmailFallbackService;
import com.user.dto.EmailRequestDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailIntegrationService {

    private final EmailClient emailClient;
    private final EmailFallbackService emailFallback;

    public EmailIntegrationService(EmailClient emailClient, EmailFallbackService emailFallback) {
        this.emailClient = emailClient;
        this.emailFallback = emailFallback;
    }

    @Retry(name = "emailService")
    @CircuitBreaker(name = "emailService", fallbackMethod = "sendMailFallback")
    public void sendMail(
            EmailRequestDTO email) {

        emailClient.sendMail(email);
    }

    public void sendMailFallback(
            EmailRequestDTO email,
            Exception ex) {

        emailFallback.emailFallback(
                email,
                ex
        );


    }
}

