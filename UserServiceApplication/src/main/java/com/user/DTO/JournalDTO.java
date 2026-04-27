package com.user.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class JournalDTO {

    private Long journalid;
    private String title;
    private String content;
    private Date date;
    private Long userid;

}
