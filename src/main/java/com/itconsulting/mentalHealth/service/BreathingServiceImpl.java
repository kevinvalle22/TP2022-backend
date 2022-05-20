package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.Breathing;
import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.Goal;
import com.itconsulting.mentalHealth.core.repository.BreathingRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.BreathingService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BreathingServiceImpl implements BreathingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BreathingRepository breathingRepository;

    @Override
    public Page<Breathing> getAllBreathings(Pageable pageable) {
        return breathingRepository.findAll(pageable);
    }

    @Override
    public Page<Breathing> getAllBreathingsByUserId(Long userId, Pageable pageable) {
        return breathingRepository.findByUserId(userId,pageable);
    }

    @Override
    public Breathing getBreathingByIdAndUserId(Long breathingId, Long userId) {
        return breathingRepository.findByIdAndUserId(breathingId,userId).orElseThrow(
                ()->new ResourceNotFoundException("breathing not Found by Id"+ breathingId +" and userId" + userId));
    }

    @Override
    public Breathing updateBreathingById(Breathing breathing, Long breathingId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Breathing breathing1= breathingRepository.findById(breathingId).orElseThrow(()-> new ResourceNotFoundException("Breathing", "Id", breathingId));
        breathing1.setName(breathing.getName());
        breathing1.setDuration(breathing.getDuration());
        return breathingRepository.save(breathing1);
    }

    @Override
    public Breathing saveBreathing(Breathing breathing, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        breathing.setUser(user);
        return breathingRepository.save(breathing);
    }

    @Override
    public ResponseEntity<?> deleteBreathing(Long breathingId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Breathing breathing1= breathingRepository.findById(breathingId).orElseThrow(()-> new ResourceNotFoundException("Breathing", "Id", breathingId));
        breathingRepository.delete(breathing1);
        return ResponseEntity.ok().build();
    }
}
