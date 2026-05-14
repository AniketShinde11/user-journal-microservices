package com.journal.service;

import com.journal.dto.Journaldto;
import com.journal.entity.JournalEntity;
import com.journal.repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class JournalService {

    private final JournalRepository journalRepository;


    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Transactional
    public Journaldto createJournal(Journaldto journalEntity) {

        JournalEntity journalEntity2=new JournalEntity();
        journalEntity2.setTitle(journalEntity.getTitle());
        journalEntity2.setContent(journalEntity.getContent());
        journalEntity2.setUserId(journalEntity.getUserId());
        journalEntity2.setCreatedAt(journalEntity.getCreatedAt());

        log.info("Creating journal for userId: {}", journalEntity.getUserId());


        JournalEntity journalEntity1=journalRepository.save(journalEntity2);

        return maptoResponse(journalEntity1);

    }

    @Transactional(readOnly = true)
    public List<Journaldto> getAllJournalByUserId(Long userId) {

     return journalRepository.findByUserId(userId).stream().map(this::maptoResponse).toList();

    }

    @Transactional
    public Journaldto updateJournal(Long userId, Long journalId, JournalEntity updatedJournal) {

        JournalEntity existingjournal = journalRepository.findByJournalIdAndUserId(journalId, userId).orElseThrow(() -> new RuntimeException("Journal Not Found"));


        existingjournal.setContent(updatedJournal.getContent());
        existingjournal.setTitle(updatedJournal.getTitle());

      JournalEntity journalEntity = journalRepository.save(existingjournal);

      return maptoResponse(journalEntity);


    }

    @Transactional
    public void deleteJournalByuserId(Long userId,Long journalId){
        journalRepository.deleteByJournalIdAndUserId(userId,journalId);
    }


    private Journaldto maptoResponse(JournalEntity journalEntity){

        Journaldto journaldto=new Journaldto();

        journaldto.setContent(journalEntity.getContent());
        journaldto.setTitle(journalEntity.getTitle());
        journaldto.setCreatedAt(journalEntity.getCreatedAt());

        return journaldto   ;

    }





}
