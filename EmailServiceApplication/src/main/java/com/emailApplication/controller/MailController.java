package com.emailApplication.controller;

import com.emailApplication.dto.EmailRequestDTO;
import com.emailApplication.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

private final MailService mailService;

public MailController(MailService mailService) {
    this.mailService = mailService;
}

@PostMapping("/send")
public ResponseEntity<String> sendMail( @Valid @RequestBody EmailRequestDTO dto){

    mailService.sendMail(
            dto.getToEmail(),
            dto.getSubject(),
            dto.getMessage()
    );

    return ResponseEntity.ok("Email sent successfully");
}
}
