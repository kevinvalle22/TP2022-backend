package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.Reminder;
import com.itconsulting.mentalHealth.core.repository.ReminderRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.ReminderService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReminderRepository reminderRepository;
    @Override
    public Page<Reminder> getAllReminders(Pageable pageable) {
        return reminderRepository.findAll(pageable);
    }

    @Override
    public Page<Reminder> getAllRemindersByUserId(Long userId, Pageable pageable) {
        return reminderRepository.findByUserId(userId,pageable);
    }

    @Override
    public Reminder getReminderByIdAndUserId(Long reminderId, Long userId) {
        return reminderRepository.findByIdAndUserId(reminderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reminder not found with Id " + reminderId +
                                " and UserId " + userId));
    }

    @Override
    public Reminder updateReminderById(Reminder reminder, Long reminderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Reminder reminder1 = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "Id", reminderId));
        reminder1.setReminderDate(reminder.getReminderDate());
        reminder1.setMessage(reminder.getMessage());
        return reminderRepository.save(reminder1);


    }

    @Override
    public Reminder saveReminder(Reminder reminder, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        reminder.setUser(user);



        return reminderRepository.save(reminder);

    }

    @Override
    public ResponseEntity<?> deleteReminder(Long reminderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "Id", reminderId));
        reminderRepository.delete(reminder);
        return ResponseEntity.ok().build();
    }
}
