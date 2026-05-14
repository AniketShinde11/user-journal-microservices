package com.emailApplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDTO {

    @Email
    @NotBlank
    private String toEmail;

    @NotBlank
    private String subject;
    @NotBlank
    private String message;

}