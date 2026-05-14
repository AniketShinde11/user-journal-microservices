package com.user.service;

import com.user.dto.UserDTO;
import com.user.entity.UserEntity;
import com.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {



    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {

        UserDTO dto = new UserDTO();
        dto.setUsername("aniket");
        dto.setPassword("123");
        dto.setEmail("aniket@gmail.com");
        dto.setMobileNo("9876543210");

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("aniket");
        savedUser.setEmail("aniket@gmail.com");

        when(passwordEncoder.encode("123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        var response = userService.createUser(dto);

        assertNotNull(response);
        assertEquals(
                "aniket",
                response.getUsername()
        );
    }


}
