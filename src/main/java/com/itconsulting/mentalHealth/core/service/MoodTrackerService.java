package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.MoodTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MoodTrackerService {
    Page<MoodTracker> getAllMoodTrackers(Pageable pageable);
    Page<MoodTracker> getAllMoodTrackersByUserId(Long userId, Pageable pageable);


    MoodTracker getMoodTrackerByIdAndUserId(Long moodTrackerId,Long userId);
    MoodTracker updateMoodTrackerById(MoodTracker moodTracker,Long moodTrackerId, Long userId);
    MoodTracker saveMoodTracker(MoodTracker moodTracker, Long userId);

    ResponseEntity<?> deleteMoodTracker(Long moodTrackerId, Long userId);
}
