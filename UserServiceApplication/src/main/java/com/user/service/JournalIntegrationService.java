package com.user.service;

import com.user.client.JournaClient;
import com.user.client.JournalFallbackService;
import com.user.dto.JournalDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JournalIntegrationService {

    private final JournaClient journaClient;
    private final JournalFallbackService journalfallback;

    public JournalIntegrationService(JournaClient journaClient, JournalFallbackService journalfallback) {
        this.journaClient = journaClient;
        this.journalfallback = journalfallback;
    }

    @Retry(name = "journalService")
    @CircuitBreaker(name = "journalService", fallbackMethod = "createJournalFallback")
    public JournalDTO createJournal(JournalDTO journalDTO){
        log.info("Calling journal-service for userId: {}", journalDTO.getUserid());
        return journaClient.createJournal(journalDTO);
    }
    public JournalDTO createJournalFallback(
            JournalDTO journalDTO,
            Exception ex){

        return journalfallback.journalFallback(ex);
    }






    @Retry(name = "journalService")
    @CircuitBreaker(name = "journalService",fallbackMethod = "getAllJournalFallback")
    public List<JournalDTO> getAllJournal(Long userid){

        return journaClient.getAllJournal(userid);
    }
    public List<JournalDTO> getAllJournalFallback(Long userid, Exception ex) {
        return journalfallback.getAllJournalFallback(userid, ex);
    }

    @Retry(name = "journalService")
    @CircuitBreaker(name = "journalService", fallbackMethod = "updateJournalFallback")
    public JournalDTO updateJournalByUserId(Long userId,JournalDTO updatedJournal){
        return journaClient.updateJournal(userId,updatedJournal);
    }

    public JournalDTO updateJournalFallback(Long userId, JournalDTO updatedJournal, Exception ex){

        return journalfallback.journalFallback(ex);
    }

    }





