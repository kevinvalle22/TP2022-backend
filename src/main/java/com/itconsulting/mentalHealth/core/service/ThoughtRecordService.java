package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.ThoughtRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ThoughtRecordService {
    Page<ThoughtRecord> getAllThoughtRecords(Pageable pageable);
    Page<ThoughtRecord> getAllThoughtRecordsByUserId(Long userId, Pageable pageable);


    ThoughtRecord getThoughtRecordByIdAndUserId(Long thoughtRecordId,Long userId);
    ThoughtRecord updateThoughtRecordById(ThoughtRecord thoughtRecord,Long thoughtRecordId, Long userId);
    ThoughtRecord saveThoughtRecord(ThoughtRecord thoughtRecord, Long userId);

    ResponseEntity<?> deleteThoughtRecord(Long thoughtRecordId, Long userId);
}
