package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.Breathing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BreathingService {
    Page<Breathing> getAllBreathings(Pageable pageable);
    Page<Breathing> getAllBreathingsByUserId(Long userId, Pageable pageable);


    Breathing getBreathingByIdAndUserId(Long breathingId,Long userId);
    Breathing updateBreathingById(Breathing breathing,Long breathingId, Long userId);
    Breathing saveBreathing(Breathing breathing, Long userId);

    ResponseEntity<?> deleteBreathing(Long breathingId, Long userId);
}
