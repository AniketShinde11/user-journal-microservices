package com.user.client;


import com.user.dto.JournalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="journal-service",url="${journal.service.url}",fallback = JournalFallback.class)
public interface JournaClient {

    @PostMapping("/journal/create")
    JournalDTO createJournal(@RequestBody JournalDTO journalDTO);

    @GetMapping("/journal/user/{userid}")
    List<JournalDTO> getAllJournal(@PathVariable Long userid);

    @PutMapping("/journal/user/{userId}")
    JournalDTO updateJournal(@PathVariable Long userId, @RequestBody JournalDTO updatedJournal);

}
