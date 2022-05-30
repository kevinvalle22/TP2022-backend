package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.ExerciseActivity;
import com.itconsulting.mentalHealth.core.repository.ExerciseActivityRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.ExerciseActivityService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExerciseActivityServiceImpl implements ExerciseActivityService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExerciseActivityRepository exerciseActivityRepository;

    @Override
    public Page<ExerciseActivity> getAllExerciseActivities(Pageable pageable) {
        return exerciseActivityRepository.findAll(pageable);
    }

    @Override
    public Page<ExerciseActivity> getAllExerciseActivitiesByUserId(Long userId, Pageable pageable) {
        return exerciseActivityRepository.findByUserId(userId,pageable);
    }

    @Override
    public ExerciseActivity getExerciseActivityByIdAndUserId(Long exerciseActivityId, Long userId) {
        return exerciseActivityRepository.findByIdAndUserId(exerciseActivityId,userId).orElseThrow(() -> new ResourceNotFoundException("Registry not Found"+ exerciseActivityId
                + " and UserId "+ userId));
    }

    @Override
    public ExerciseActivity updateExerciseActivityById(ExerciseActivity exerciseActivity, Long exerciseActivityId, Long userId) {
         userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
         ExerciseActivity exerciseActivity1= exerciseActivityRepository.findById(exerciseActivityId).orElseThrow(() -> new ResourceNotFoundException("Exercise Registry Not Found"));

         exerciseActivity1.setExerciseDate(exerciseActivity.getExerciseDate());
         exerciseActivity1.setDuration(exerciseActivity.getDuration());

        return exerciseActivityRepository.save(exerciseActivity1);
    }

    @Override
    public ExerciseActivity saveExerciseActivity(ExerciseActivity exerciseActivity, Long userId) {
        exerciseActivity.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));
        return exerciseActivityRepository.save(exerciseActivity);
    }

    @Override
    public ResponseEntity<?> deleteExerciseActivity(Long exerciseActivityId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        ExerciseActivity exerciseActivity1= exerciseActivityRepository.findById(exerciseActivityId).orElseThrow(() -> new ResourceNotFoundException("Exercise Registry"));

        exerciseActivityRepository.delete(exerciseActivity1);

        return ResponseEntity.ok().build();
    }
}
