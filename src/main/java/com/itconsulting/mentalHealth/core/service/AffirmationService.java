package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.Affirmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AffirmationService {
    Page<Affirmation> getAllAffirmations(Pageable pageable);
    Page<Affirmation> getAllAffirmationsByUserId(Long userId, Pageable pageable);

    Affirmation getTheDayOfWeek(Long affirmationId, Long userId);
    Affirmation getAffirmationByIdAndUserId(Long affirmationId,Long userId);
    Affirmation updateAffirmationById(Affirmation affirmation,Long affirmationId, Long userId);
    Affirmation saveAffirmation(Affirmation affirmation, Long userId);

    ResponseEntity<?> deleteAffirmation(Long affirmationId, Long userId);
}
