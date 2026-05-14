package com.journal.repository;

import com.journal.entity.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntity,Long> {

    List<JournalEntity> findByUserId(Long userid);

    Optional<JournalEntity> findByJournalIdAndUserId(Long journalId, Long userId);

     void deleteByJournalIdAndUserId(Long journalId, Long userId);

}
