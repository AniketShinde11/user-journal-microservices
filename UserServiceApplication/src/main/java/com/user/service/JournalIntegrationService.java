package com.user.service;

import com.user.client.JournaClient;
import com.user.dto.JournalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JournalIntegrationService {

    private final JournaClient journaClient;

    public JournalIntegrationService(JournaClient journaClient) {
        this.journaClient = journaClient;
    }

    public JournalDTO createJournal(JournalDTO journalDTO){
        log.info("Calling journal-service for userId: {}", journalDTO.getUserid());
        return journaClient.createJournal(journalDTO);
    }

    public List<JournalDTO> getAllJournal(Long userid){
        return journaClient.getAllJournal(userid);
    }


    public JournalDTO updateJournalByUserId(Long userId,JournalDTO updatedJournal){
        return journaClient.updateJournal(userId,updatedJournal);
    }



}
