package com.user.Client;


import com.user.DTO.JournalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="journal-service",url="http://localhost:8082")
public interface JournaClient {

    @PostMapping("/journal/create")
    JournalDTO createJournal(@RequestBody JournalDTO journalDTO);

    @GetMapping("/journal/user/{userid}")
    List<JournalDTO> getAllJournal(@PathVariable Long userid);

}
