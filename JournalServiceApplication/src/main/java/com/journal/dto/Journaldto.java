package com.journal.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Journaldto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private LocalDateTime createdAt;
    private Long userId;

}
