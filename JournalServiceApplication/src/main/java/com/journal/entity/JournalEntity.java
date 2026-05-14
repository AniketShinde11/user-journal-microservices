package com.journal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "journal_entries")
public class JournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private Long userId;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
