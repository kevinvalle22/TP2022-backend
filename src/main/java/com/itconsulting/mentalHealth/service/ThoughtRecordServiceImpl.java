package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.Goal;
import com.itconsulting.mentalHealth.core.entity.ThoughtRecord;
import com.itconsulting.mentalHealth.core.repository.ThoughtRecordRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.ThoughtRecordService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ThoughtRecordServiceImpl implements ThoughtRecordService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThoughtRecordRepository thoughtRecordRepository;

    @Override
    public Page<ThoughtRecord> getAllThoughtRecords(Pageable pageable) {
        return thoughtRecordRepository.findAll(pageable);
    }

    @Override
    public Page<ThoughtRecord> getAllThoughtRecordsByUserId(Long userId, Pageable pageable) {
        return thoughtRecordRepository.findByUserId(userId,pageable);
    }

    @Override
    public ThoughtRecord getThoughtRecordByIdAndUserId(Long thoughtRecordId, Long userId) {
        return thoughtRecordRepository.findByIdAndUserId(thoughtRecordId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Thought Record not found with Id " + thoughtRecordId +
                                " and UserId " + userId));
    }

    @Override
    public ThoughtRecord updateThoughtRecordById(ThoughtRecord thoughtRecord, Long thoughtRecordId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        ThoughtRecord thoughtRecord1=  thoughtRecordRepository.findById(thoughtRecordId).orElseThrow(()-> new ResourceNotFoundException("Thought", "Id", thoughtRecordId));
        thoughtRecord1.setMessage(thoughtRecord.getMessage());
        return thoughtRecordRepository.save(thoughtRecord1);
    }

    @Override
    public ThoughtRecord saveThoughtRecord(ThoughtRecord thoughtRecord, Long userId) {
      DAOUser user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
      thoughtRecord.setUser(user);
      return thoughtRecordRepository.save(thoughtRecord);
    }

    @Override
    public ResponseEntity<?> deleteThoughtRecord(Long thoughtRecordId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        ThoughtRecord thoughtRecord1=  thoughtRecordRepository.findById(thoughtRecordId).orElseThrow(()-> new ResourceNotFoundException("Thought", "Id", thoughtRecordId));
        thoughtRecordRepository.delete(thoughtRecord1);
        return ResponseEntity.ok().build();
    }
}
