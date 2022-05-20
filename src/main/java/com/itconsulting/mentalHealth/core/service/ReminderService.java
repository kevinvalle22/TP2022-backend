package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ReminderService {
    Page<Reminder> getAllReminders(Pageable pageable);
    Page<Reminder> getAllRemindersByUserId(Long userId, Pageable pageable);


    Reminder getReminderByIdAndUserId(Long reminderId,Long userId);
    Reminder updateReminderById(Reminder reminder,Long reminderId, Long userId);
    Reminder saveReminder(Reminder reminder, Long userId);

    ResponseEntity<?> deleteReminder(Long reminderId, Long userId);
}
