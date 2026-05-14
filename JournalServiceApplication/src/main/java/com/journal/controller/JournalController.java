package com.journal.controller;

import com.journal.dto.Journaldto;
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
    public ResponseEntity<Journaldto> createjournal(@RequestBody Journaldto journalEntity){
        log.info("Journal create request received for userId: {}", journalEntity.getUserId());
        return ResponseEntity.ok(journalService.createJournal(journalEntity));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Journaldto>> getAllJournalByUserId(@PathVariable Long userId){
        return  ResponseEntity.ok(journalService.getAllJournalByUserId(userId));
    }

    @PutMapping("/user/{userId}/{journalId}")
    public ResponseEntity< Journaldto> updateJournal(@PathVariable Long userId,@PathVariable Long journalId ,@RequestBody JournalEntity updatedjournal){
        return ResponseEntity.ok(journalService.updateJournal(userId,journalId,updatedjournal));
    }

    @DeleteMapping("/user/{userId}/{journalId}")
    public void deleteJournalByUserId(@PathVariable Long userId,@PathVariable Long journalId){
        journalService.deleteJournalByuserId(userId,journalId);
    }
}
