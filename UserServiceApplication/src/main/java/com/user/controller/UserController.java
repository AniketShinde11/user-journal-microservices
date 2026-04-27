package com.user.controller;


import com.user.DTO.JournalDTO;
import com.user.DTO.UserDTO;
import com.user.entity.UserEntity;
import com.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("create-user")
    public ResponseEntity<UserEntity> createuser(@RequestBody UserEntity user){
        log.info("Create user API called for username: {}", user.getUsername());
       return   ResponseEntity.ok( userService.createuser(user));

    }
    @GetMapping("get-user/{Id}")
    public ResponseEntity<UserEntity> getuser(@PathVariable Long Id){
        return ResponseEntity.ok(userService.getById(Id));
    }

    @GetMapping("/get-user")
    public ResponseEntity<List<UserEntity>> getAllUser(){
       return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/update-user/{Id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long Id, @RequestBody UserDTO updateuser){

        return ResponseEntity.ok(userService.updateuser(Id,updateuser));
    }

    @DeleteMapping("/delete-user/id/{Id}")
    public ResponseEntity<String> deleteuser(@PathVariable Long Id){

        log.info("Delete user API called for id: {}", Id);
        userService.deleteById(Id);

        return ResponseEntity.ok("User Deleted Sucessfully");
    }

    @DeleteMapping("/delete-user/username/{username}")
    public ResponseEntity<String> deleteuserByUsername(@PathVariable String username){
        userService.deleteByUsername(username);

        return ResponseEntity.ok("User Deleted Sucessfully");
    }

   @PostMapping("/{userid}/journal")
    public ResponseEntity<JournalDTO> createjournal(@PathVariable Long userid,@RequestBody JournalDTO journalDTO){
        journalDTO.setUserid(userid);
         return ResponseEntity.ok(userService.createJournal(journalDTO));

    }

    @GetMapping("/{userid}/journal")
    public ResponseEntity<List<JournalDTO>> getAllJournal(@PathVariable  Long userid){
        return ResponseEntity.ok(userService.getAllJournal(userid));
    }



}
