package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import com.itconsulting.mentalHealth.core.repository.SleepRecordRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.SleepRecordService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class SleepRecordServiceImpl implements SleepRecordService {

    @Autowired
    SleepRecordRepository sleepRecordRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Page<SleepRecord> getAllSleepRecords(Pageable pageable) {
        return sleepRecordRepository.findAll(pageable);
    }

    @Override
    public Page<SleepRecord> getAllSleepRecordsByUserId(Long userId, Pageable pageable) {
        return sleepRecordRepository.findByUserId(userId,pageable);
    }

    @Override
    public SleepRecord getSleepRecordByIdAndUserId(Long sleepRecordId, Long userId) {
        return sleepRecordRepository.findByIdAndUserId(sleepRecordId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sleep not found with Id " + sleepRecordId +
                                " and UserId " + userId));
    }

    @Override
    public SleepRecord updateSleepRecordById(SleepRecord sleepRecord, Long sleepRecordId, Long userId) {
        SleepRecord result = sleepRecordRepository.findById(sleepRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Sleep Record with ", "Id", sleepRecordId));

        result.setStartDate(sleepRecord.getStartDate());
        result.setEndDate(sleepRecord.getEndDate());
        result.setMessage(sleepRecord.getMessage());
        // use start date and end date to calculate duration in hours
        float time_difference= sleepRecord.getEndDate().getHours() - sleepRecord.getStartDate().getHours();
        float time_differentiates= sleepRecord.getEndDate().getMinutes() - sleepRecord.getStartDate().getMinutes();

        float minutes =  time_differentiates/100;
        System.out.print("las horas son "+time_difference + " y los minutos"+minutes);
        String durationString= String.valueOf(time_difference+minutes);
        sleepRecord.setDuration(durationString);
        int dayOfWeek = sleepRecord.getStartDate().getDay();
        DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        //DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        result.setDayOfTheWeek(String.valueOf(dayOfWeek1));;

        return sleepRecordRepository.save(result);
    }
    @Override
    public SleepRecord getTheDayOfWeek(Long sleepRecordId, Long userId) {
        SleepRecord sleepRecord= sleepRecordRepository.findByIdAndUserId(sleepRecordId,userId).orElseThrow(() -> new ResourceNotFoundException("Registry not Found"+ sleepRecordId
                + " and UserId "+ userId));
        // get the day of week
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sleepRecord.getStartDate());
        int dayOfWeek = sleepRecord.getStartDate().getDay();
        DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        //DayOfWeek dayOfWeek1 = DayOfWeek.of(dayOfWeek);
        sleepRecord.setDayOfTheWeek(String.valueOf(dayOfWeek1));;



        return sleepRecordRepository.save(sleepRecord);
    }
    @Override
    public SleepRecord saveSleepRecord(SleepRecord sleepRecord, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        sleepRecord.setUser(user);

        // use start date and end date to calculate duration in hours
        float time_difference= sleepRecord.getEndDate().getHours() - sleepRecord.getStartDate().getHours();
        float time_differentiates= sleepRecord.getEndDate().getMinutes() - sleepRecord.getStartDate().getMinutes();

        float minutes =  time_differentiates/100;
        System.out.print("las horas son "+time_difference + " y los minutos"+minutes);
        String durationString= String.valueOf(time_difference+minutes);
        sleepRecord.setDuration(durationString);
      
        return sleepRecordRepository.save(sleepRecord);
    }

    @Override
    public ResponseEntity<?> deleteSleepRecord(Long sleepRecordId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        SleepRecord sleepRecord = sleepRecordRepository.findById(sleepRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Sleep", "Id", sleepRecordId));
        sleepRecordRepository.delete(sleepRecord);
        return ResponseEntity.ok().build();
    }
}
