package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.ExerciseActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ExerciseActivityService {
    Page<ExerciseActivity> getAllExerciseActivities(Pageable pageable);
    Page<ExerciseActivity> getAllExerciseActivitiesByUserId(Long userId, Pageable pageable);


    ExerciseActivity getExerciseActivityByIdAndUserId(Long exerciseActivityId,Long userId);
    ExerciseActivity updateExerciseActivityById(ExerciseActivity exerciseActivity,Long exerciseActivityId, Long userId);
    ExerciseActivity saveExerciseActivity(ExerciseActivity exerciseActivity, Long userId);

    ResponseEntity<?> deleteExerciseActivity(Long exerciseActivityId, Long userId);
}
