package com.user.service;

import com.user.client.EmailClient;
import com.user.client.JournaClient;
import com.user.dto.*;
import com.user.exception.ResourceNotFoundException;
import com.user.entity.UserEntity;
import com.user.repository.UserRepository;

import com.user.repository.UserSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;



import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailClient emailClient;
    private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, EmailClient emailClient, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
      this.emailClient = emailClient;
      this.passwordEncoder = passwordEncoder;

  }

    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO){
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setMobileNo(userDTO.getMobileNo());
        if(userDTO.getRoles()==null || userDTO.getRoles().isEmpty()){
            user.setRoles(List.of("ROLE_USER"));
        }
        UserEntity savedUser = userRepository.save(user);

        EmailRequestDTO email = new EmailRequestDTO();

        email.setToEmail(savedUser.getEmail());

        email.setSubject("Welcome to Our Service");
        email.setMessage(
                "Hi " + savedUser.getUsername() + ",\n\n" +
                        "Your account has been created successfully.\n\n" +
                        "Thank you for joining us.\n\n" +
                        "Best Regards,\n" +
                        "Aniket Shinde"
        );


        try {
            emailClient.sendMail(email);  // jar email-service down asel tar
    } catch (Exception e)  {               // user create pan fail hoil

            log.error(
                    "Failed to send welcome email for user: {}",
                    savedUser.getUsername()
            );
        }

        return mapToResponse(savedUser);


    }

    public UserResponseDTO getByUserId(Long Id){
        log.info("Fetching user by id: {}", Id);
        UserEntity user =userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ Id));

        return mapToResponse(user);
    }

    public Page<UserResponseDTO> getAllUser(int page, int size,String field){
      Pageable pageable= PageRequest.of(page,size, Sort.by(field));
         return userRepository.findAll(pageable).map(this::mapToResponse);

    }

    @Transactional
    public UserEntity updateUser(Long Id, UserDTO updatedUser){
           UserEntity existingUser =userRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "));

        if(updatedUser.getPassword()!=null) {
            existingUser.setPassword(
                    passwordEncoder.encode(updatedUser.getPassword())
            );
        }
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());

            return userRepository.save(existingUser);



    }
    public void deleteByUserId(Long userId){

        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                                new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.delete(existingUser);


    }

    public void deleteByUsername(String username) {
                UserEntity user=userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found with username: " + username));
        userRepository.delete(user);

    }




    private UserResponseDTO mapToResponse(UserEntity user) {

        UserResponseDTO response = new UserResponseDTO();


        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMobileNo(user.getMobileNo());
        response.setRoles(user.getRoles());

        return response;
    }







}
