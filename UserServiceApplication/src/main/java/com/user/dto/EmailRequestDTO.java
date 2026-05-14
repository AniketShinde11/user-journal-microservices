package com.user.dto;



import lombok.Data;

@Data
public class EmailRequestDTO {

    private String toEmail;
    private String subject;
    private String message;
}