package com.user.client;

import com.user.dto.EmailRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailFallbackService {

    public void emailFallback(
            EmailRequestDTO email,
            Exception ex) {

        log.error(
                "Email Service Down",
                ex
        );
    }
}
