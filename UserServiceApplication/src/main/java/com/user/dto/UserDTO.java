package com.user.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "Username required")
    private String password;
    @Email(message = "Invalid email")
    private String email;
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    private  String mobileNo;
    private List<String> roles=new ArrayList<>();
}
