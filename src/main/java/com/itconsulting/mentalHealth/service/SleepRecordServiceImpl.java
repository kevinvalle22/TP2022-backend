package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import com.itconsulting.mentalHealth.core.entity.TestResult;
import com.itconsulting.mentalHealth.core.repository.SleepRecordRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.SleepRecordService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        // use start date and end date to calculate duration in hours
        Date startDate = sleepRecord.getStartDate();
        Date endDate = sleepRecord.getEndDate();
        long duration = (endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000);
        // convert to String
        String durationString = String.valueOf(duration);
        sleepRecord.setDuration(durationString);
        return sleepRecordRepository.save(result);
    }

    @Override
    public SleepRecord saveSleepRecord(SleepRecord sleepRecord, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        sleepRecord.setUser(user);

        // use start date and end date to calculate duration in hours
        Date startDate = sleepRecord.getStartDate();
        Date endDate = sleepRecord.getEndDate();
        long duration = (endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000);
        // convert to String
        String durationString = String.valueOf(duration);
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
