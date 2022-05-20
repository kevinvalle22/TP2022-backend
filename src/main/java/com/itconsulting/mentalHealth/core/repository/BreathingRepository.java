package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.Breathing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreathingRepository extends JpaRepository<Breathing,Long> {
    Page<Breathing> findByUserId(Long userId, Pageable pageable);

    Optional<Breathing> findByIdAndUserId(Long id, Long userId);
}
