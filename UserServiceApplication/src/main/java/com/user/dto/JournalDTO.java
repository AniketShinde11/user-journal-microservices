package com.user.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class JournalDTO {

    private Long journalid;
    private String title;
    private String content;
    private LocalDateTime date;
    private Long userid;

}
