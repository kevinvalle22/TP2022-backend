package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Optional<Goal> findByIdAndUserId(Long id, Long userId);
}
