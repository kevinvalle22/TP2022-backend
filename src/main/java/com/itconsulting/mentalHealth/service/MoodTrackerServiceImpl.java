package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.MoodTracker;
import com.itconsulting.mentalHealth.core.entity.Reminder;
import com.itconsulting.mentalHealth.core.repository.MoodTrackerRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.MoodTrackerService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MoodTrackerServiceImpl implements MoodTrackerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodTrackerRepository moodTrackerRepository;


    @Override
    public Page<MoodTracker> getAllMoodTrackers(Pageable pageable) {
        return moodTrackerRepository.findAll(pageable);
    }

    @Override
    public Page<MoodTracker> getAllMoodTrackersByUserId(Long userId, Pageable pageable) {
        return moodTrackerRepository.findByUserId(userId,pageable);
    }

    @Override
    public MoodTracker getMoodTrackerByIdAndUserId(Long moodTrackerId, Long userId) {
        return moodTrackerRepository.findByIdAndUserId(moodTrackerId,userId).orElseThrow(
                () -> new ResourceNotFoundException( "MoodTracker not found with Id " + moodTrackerId +
                        " and UserId " + userId));
    }

    @Override
    public MoodTracker updateMoodTrackerById(MoodTracker moodTracker, Long moodTrackerId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        MoodTracker moodTracker1 = moodTrackerRepository.findById(moodTrackerId)
                .orElseThrow(() -> new ResourceNotFoundException("MoodTracker", "Id", moodTrackerId));
        moodTracker1.setMoodTrackerDate(moodTracker.getMoodTrackerDate());
        moodTracker1.setMessage(moodTracker.getMessage());
        moodTracker1.setMood(moodTracker.getMood());
        return moodTrackerRepository.save(moodTracker1);
    }

    @Override
    public MoodTracker saveMoodTracker(MoodTracker moodTracker, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        moodTracker.setUser(user);



        return moodTrackerRepository.save(moodTracker);
    }

    @Override
    public ResponseEntity<?> deleteMoodTracker(Long moodTrackerId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        MoodTracker moodTracker = moodTrackerRepository.findById(moodTrackerId)
                .orElseThrow(() -> new ResourceNotFoundException("MoodTracker", "Id", moodTrackerId));
        moodTrackerRepository.delete(moodTracker);
        return ResponseEntity.ok().build();
    }
}
