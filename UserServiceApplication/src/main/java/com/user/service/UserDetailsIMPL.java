package com.user.service;

import com.user.entity.UserEntity;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsIMPL implements UserDetailsService {


      private final UserRepository userRepository;

    public UserDetailsIMPL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
       UserEntity user =userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User Not Found"));



        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                )
                .build();
    }
}
