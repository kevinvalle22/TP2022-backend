package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Long> {
    Page<Reminder> findByUserId(Long userId, Pageable pageable);

    Optional<Reminder> findByIdAndUserId(Long id, Long userId);
}
