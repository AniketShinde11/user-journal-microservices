package com.user.client;

import com.user.dto.JournalDTO;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JournalFallback implements JournaClient {
    @Override
    public JournalDTO createJournal(JournalDTO journalDTO) {
        throw new RuntimeException( "Journal Service is unavailable");
    }

    @Override
    public List<JournalDTO> getAllJournal(Long userid) {
        return Collections.emptyList();
    }

    @Override
    public JournalDTO updateJournal(Long userId, JournalDTO updatedJournal) {
        throw new RuntimeException( "Journal Service is unavailable");
    }
}
