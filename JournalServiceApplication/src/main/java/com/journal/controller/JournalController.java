package com.journal.controller;

import com.journal.entity.JournalEntity;
import com.journal.service.JournalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }


    @PostMapping("/create")
    public ResponseEntity<JournalEntity> createjournal(@RequestBody JournalEntity journalEntity){
        log.info("Journal create request received for userId: {}", journalEntity.getUserid());
        return ResponseEntity.ok(journalService.createJournal(journalEntity));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JournalEntity>> getAllJournalByUserId(@PathVariable Long userid){
        return  ResponseEntity.ok(journalService.getAllJournalByUserId(userid));
    }

}
