package com.user.client;

import com.user.dto.JournalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JournalFallbackService {

    public List<JournalDTO> getAllJournalFallback(
            Long userId,
            Exception ex){

        log.error(
                "Journal Service Down",
                ex
        );

        return List.of();
    }

    public JournalDTO journalFallback(
            Exception ex){

        log.error(
                "Journal Service Down",
                ex
        );

        throw new RuntimeException(
                "Journal Service Unavailable"
        );


    }
}
