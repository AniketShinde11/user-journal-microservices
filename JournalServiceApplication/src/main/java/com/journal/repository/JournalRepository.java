package com.journal.repository;

import com.journal.entity.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntity,Long> {
    List<JournalEntity> findByUserid(Long userid);

}
