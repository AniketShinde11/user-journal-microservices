package com.user.service;

import com.user.Client.JournaClient;
import com.user.DTO.JournalDTO;
import com.user.DTO.UserDTO;
import com.user.entity.UserEntity;
import com.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JournaClient journaClient;
    private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, JournaClient journaClient, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.journaClient=journaClient;
      this.passwordEncoder = passwordEncoder;
  }

    @Transactional
    public UserEntity createuser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving user in database: {}", user.getUsername());
        return userRepository.save(user);

    }

    public UserEntity getById(Long Id){
        log.info("Fetching user by id: {}", Id);
        return userRepository.findById(Id)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity updateuser(Long Id, UserDTO updatedUser){
           UserEntity existingUser =userRepository.findById(Id).orElseThrow(()-> new RuntimeException("User Not Found"));

           existingUser.setPassword( passwordEncoder.encode(updatedUser.getPassword()));

           return userRepository.save(existingUser);



    }
    public void deleteById(Long Id){
        userRepository.deleteById(Id);


    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);

    }

    public JournalDTO createJournal(JournalDTO journalDTO){
        log.info("Calling journal-service for userId: {}", journalDTO.getUserid());
        return journaClient.createJournal(journalDTO);
    }

    public List<JournalDTO> getAllJournal(Long userid){
        return journaClient.getAllJournal(userid);
    }

}
