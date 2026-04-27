package com.journal.service;

import com.journal.entity.JournalEntity;
import com.journal.repository.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JournalService {

    private final JournalRepository journalrepository;


    public JournalService(JournalRepository journalrepository) {
        this.journalrepository = journalrepository;
    }

    @Transactional
    public JournalEntity createJournal(JournalEntity journalEntity){
       return journalrepository.save(journalEntity);
    }

    public List<JournalEntity> getAllJournalByUserId(Long userid){
        return journalrepository.findByUserid(userid);
    }


}
