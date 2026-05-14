package com.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String mobileNo;
    private List<String> roles;

}
