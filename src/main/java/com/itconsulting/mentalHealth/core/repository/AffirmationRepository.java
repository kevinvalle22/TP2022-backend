package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.Affirmation;
import com.itconsulting.mentalHealth.core.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffirmationRepository extends JpaRepository<Affirmation, Long> {
    Page<Affirmation> findByUserId(Long userId, Pageable pageable);

    Optional<Affirmation> findByIdAndUserId(Long id, Long userId);
}