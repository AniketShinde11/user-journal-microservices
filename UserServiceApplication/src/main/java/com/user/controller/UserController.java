package com.user.controller;


import com.user.dto.JournalDTO;
import com.user.dto.UserDTO;
import com.user.dto.UserResponseDTO;
import com.user.dto.UserSearchDTO;
import com.user.entity.UserEntity;
import com.user.service.JournalIntegrationService;
import com.user.service.MailService;
import com.user.service.UserSearchService;
import com.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JournalIntegrationService journalService;
    private final UserSearchService userSearchService;

    public UserController(UserService userService, MailService mailService, JournalIntegrationService journalService, UserSearchService userSearchService){
        this.userService=userService;
        this.journalService = journalService;
        this.userSearchService = userSearchService;
    }


    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO dto){
        log.info("Create user API called for username: {}", dto.getUsername());
       return   ResponseEntity.ok( userService.createUser(dto));

    }
    @GetMapping("/get-user/{Id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long Id){
        return ResponseEntity.ok(userService.getByUserId(Id));
    }

    @GetMapping("/get-user")
    public ResponseEntity<Page<UserResponseDTO>> getAllUser(@RequestParam int page, @RequestParam int size, @RequestParam String field){
       return ResponseEntity.ok(userService.getAllUser(page,size,field));
    }

    @PutMapping("/update-user/{Id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long Id, @Valid @RequestBody UserDTO updateuser){

        return ResponseEntity.ok(userService.updateUser(Id,updateuser));
    }

    @DeleteMapping("/delete-user/id/{Id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long Id){

        log.info("Delete user API called for id: {}", Id);
        userService.deleteByUserId(Id);

        return ResponseEntity.ok("User Deleted Successfully");
    }

    @DeleteMapping("/delete-user/username/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username){
        userService.deleteByUsername(username);

        return ResponseEntity.ok("User Deleted Successfully");
    }

   @PostMapping("/{userid}/journal")
    public ResponseEntity<JournalDTO> createJournal( @PathVariable Long userid,@RequestBody JournalDTO journalDTO){
        journalDTO.setUserid(userid);
         return ResponseEntity.ok(journalService.createJournal(journalDTO));

    }

    @GetMapping("/{userid}/journal")
    public ResponseEntity<List<JournalDTO>> getAllJournal(@PathVariable  Long userid){
        return ResponseEntity.ok(journalService.getAllJournal(userid));
    }

    @PutMapping("/{userid}/journal")
    public ResponseEntity<JournalDTO> updateJournal(@PathVariable Long userid,@RequestBody JournalDTO journalDTO){

        return ResponseEntity.ok(journalService.updateJournalByUserId(userid,journalDTO));
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserResponseDTO>>
    searchUsers(
            @RequestBody UserSearchDTO searchDTO) {

        return ResponseEntity.ok(
                userSearchService.searchUsers(searchDTO)
        );
    }





}
