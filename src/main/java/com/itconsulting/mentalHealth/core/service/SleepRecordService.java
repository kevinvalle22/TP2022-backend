package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SleepRecordService {

    Page<SleepRecord> getAllSleepRecords(Pageable pageable);
    Page<SleepRecord> getAllSleepRecordsByUserId(Long userId, Pageable pageable);


    SleepRecord getSleepRecordByIdAndUserId(Long sleepRecordId,Long userId);
    SleepRecord updateSleepRecordById(SleepRecord sleepRecord,Long sleepRecordId, Long userId);

    SleepRecord getTheDayOfWeek(Long sleepRecordId, Long userId);

    SleepRecord saveSleepRecord(SleepRecord sleepRecord, Long userId);

    ResponseEntity<?> deleteSleepRecord(Long sleepRecordId, Long userId);
}
