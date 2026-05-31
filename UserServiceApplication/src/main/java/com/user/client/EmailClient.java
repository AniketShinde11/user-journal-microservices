package com.user.client;


import com.user.dto.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface EmailClient {
    @PostMapping("/mail/send")
    String sendMail(@RequestBody EmailRequestDTO dto
    );
}
