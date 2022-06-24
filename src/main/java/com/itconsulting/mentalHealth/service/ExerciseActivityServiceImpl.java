package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.ExerciseActivity;
import com.itconsulting.mentalHealth.core.repository.ExerciseActivityRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.ExerciseActivityService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public ExerciseActivity getTheDayOfWeek(Long exerciseActivityId, Long userId) {
       ExerciseActivity exerciseActivity= exerciseActivityRepository.findByIdAndUserId(exerciseActivityId,userId).orElseThrow(() -> new ResourceNotFoundException("Registry not Found"+ exerciseActivityId
                + " and UserId "+ userId));
        // using the calendar class to get the day of the week with startDate
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(exerciseActivity.getStartDate());
        int dayOfWeek = exerciseActivity.getStartDate().getDay();
        DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        //DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        exerciseActivity.setDayOfTheWeek(String.valueOf(dayOfWeek1));

        // get the day of week
     // exerciseActivity.setDayOfTheWeek(String.valueOf(exerciseActivity.getStartDate().getDay()));



        return exerciseActivityRepository.save(exerciseActivity);
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

         exerciseActivity1.setStartDate(exerciseActivity.getStartDate());
         exerciseActivity1.setEndDate(exerciseActivity.getEndDate());
         exerciseActivity1.setMessage(exerciseActivity.getMessage());
        float time_difference= exerciseActivity.getEndDate().getHours() - exerciseActivity.getStartDate().getHours();
        float time_differentiates= exerciseActivity.getEndDate().getMinutes() - exerciseActivity.getStartDate().getMinutes();

        float minutes =  time_differentiates/100;
        System.out.print("las horas son "+time_difference + " y los minutos"+minutes);
        String durationString= String.valueOf(time_difference+minutes);
        exerciseActivity.setDuration(durationString);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(exerciseActivity.getStartDate());

        int dayOfWeek = exerciseActivity.getStartDate().getDay();
        DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        //DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        exerciseActivity.setDayOfTheWeek(String.valueOf(dayOfWeek1));
        return exerciseActivityRepository.save(exerciseActivity1);
    }

    @Override
    public ExerciseActivity saveExerciseActivity(ExerciseActivity exerciseActivity, Long userId) {
        exerciseActivity.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));

        float time_difference= exerciseActivity.getEndDate().getHours() - exerciseActivity.getStartDate().getHours();
        float time_differentiates= exerciseActivity.getEndDate().getMinutes() - exerciseActivity.getStartDate().getMinutes();

        float minutes =  time_differentiates/100;
        System.out.print("las horas son "+time_difference + " y los minutos"+minutes);
        String durationString= String.valueOf(time_difference+minutes);
        exerciseActivity.setDuration(durationString);
        System.out.println(time_difference + minutes);
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
