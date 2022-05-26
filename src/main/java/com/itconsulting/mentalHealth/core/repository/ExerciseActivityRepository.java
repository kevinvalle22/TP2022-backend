package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.ExerciseActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseActivityRepository extends JpaRepository<ExerciseActivity,Long> {
    Page<ExerciseActivity> findByUserId(Long userId, Pageable pageable);

    Optional<ExerciseActivity> findByIdAndUserId(Long id, Long userId);
}
