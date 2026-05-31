package com.user.service;

import com.user.client.EmailClient;
import com.user.client.JournaClient;
import com.user.dto.*;
import com.user.exception.ResourceNotFoundException;
import com.user.entity.UserEntity;
import com.user.exception.UserAlreadyExistsException;
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
    private final EmailIntegrationService emailIntegrationService;
    private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, EmailIntegrationService emailIntegrationService, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
      this.emailIntegrationService = emailIntegrationService;
      this.passwordEncoder = passwordEncoder;

  }

    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO){

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()) {

            throw new UserAlreadyExistsException(
                    "Username already exists"
            );
        }
        if(userDTO.getEmail() != null && userRepository.findByEmail(userDTO.getEmail()).isPresent()) {

            throw new UserAlreadyExistsException(
                    "Email already exists"
            );
        }

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
        email.setMessage(buildWelcomeEmail(savedUser.getUsername()));


        emailIntegrationService.sendMail(email);

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
           UserEntity existingUser =userRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ Id));

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

        response.setUserId(user.getUserid());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMobileNo(user.getMobileNo());
        response.setRoles(user.getRoles());


        return response;
    }

    private String buildWelcomeEmail(String username) {

        return "<div style='font-family:Arial,sans-serif;background:#f4f6f9;padding:30px;'>"
                + "<div style='max-width:600px;margin:auto;background:#ffffff;"
                + "border-radius:12px;overflow:hidden;"
                + "box-shadow:0 4px 12px rgba(0,0,0,0.08);'>"
        + "<div style='background:#2563eb;padding:25px;text-align:center;'>"
                + "<h1 style='color:white;margin:0;'>Welcome Aboard 🎉</h1>" + "</div>"
                + "<div style='padding:35px;color:#333;'>" + "<h2 style='margin-top:0;'>Hello "
                + username + ",</h2>" + "<p>We're delighted to welcome you to our platform.</p>"
                + "<p>Your account has been successfully created, and you can now access all the features and services available to you.</p>"
                + "<div style='background:#f8fafc;padding:20px;border-left:4px solid #2563eb;"
                + "margin:25px 0;border-radius:6px;'>" + "<p style='margin:0;'>"
                + "<strong>Getting Started:</strong><br>" + "• Complete your profile<br>"
                + "• Explore available features<br>" + "• Stay updated with our latest announcements"
                + "</p>" + "</div>"
                + "<p>If you have any questions or need assistance, our support team is always here to help.</p>"
                + "<p>Thank you for joining us. We look forward to being part of your journey.</p>"
                + "<p>Best Regards,<br>" + "<strong>The Team</strong></p>" + "</div>"
                + "<div style='background:#f1f5f9;padding:15px;text-align:center;"
                + "font-size:12px;color:#64748b;'>" + "© 2026 Company Name. All rights reserved." + "</div>"
                + "</div>" + "</div>";
    }







}
