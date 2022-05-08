package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult,Long> {
    Page<TestResult> findByUserId(Long userId, Pageable pageable);

    Optional<TestResult> findByIdAndUserId(Long id, Long userId);
}
