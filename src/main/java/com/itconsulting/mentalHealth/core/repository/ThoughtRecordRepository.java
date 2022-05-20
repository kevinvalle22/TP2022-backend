package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.ThoughtRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThoughtRecordRepository extends JpaRepository<ThoughtRecord, Long> {
    Page<ThoughtRecord> findByUserId(Long userId, Pageable pageable);

    Optional<ThoughtRecord> findByIdAndUserId(Long id, Long userId);
}