package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import com.itconsulting.mentalHealth.core.entity.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SleepRecordRepository extends JpaRepository<SleepRecord,Long> {
    Page<SleepRecord> findByUserId(Long userId, Pageable pageable);

    Optional<SleepRecord> findByIdAndUserId(Long id, Long userId);
}
