package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.Reminder;
import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import com.itconsulting.mentalHealth.core.service.ReminderService;
import com.itconsulting.mentalHealth.resource.ReminderResource;
import com.itconsulting.mentalHealth.resource.SleepRecordResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReminderController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReminderService reminderService;

    @GetMapping(value = "/reminders")
    public Page<ReminderResource> getAllReminders(Pageable pageable) {
        Page<Reminder> reminders = reminderService.getAllReminders(pageable);
        List<ReminderResource> resources = reminders.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/reminders")
    public Page<ReminderResource> getAllRemindersByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<ReminderResource> resources = reminderService.getAllRemindersByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/reminders/{reminderId}")
    public ReminderResource getReminderByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                           @PathVariable(name = "reminderId") Long reminderId) {
        return convertToResource(reminderService.getReminderByIdAndUserId(reminderId, userId));
    }

    @PostMapping("/users/{userId}/reminders")
    public ReminderResource createReminder(@PathVariable(name = "userId") Long userId,
                                           @Valid @RequestBody ReminderResource resource) {
        return convertToResource(reminderService.saveReminder(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/reminders/{reminderId}")
    public ReminderResource updateReminder(@PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "reminderId") Long reminderId,
                                                @Valid @RequestBody ReminderResource resource) {
        return convertToResource(reminderService.updateReminderById(convertToEntity(resource),reminderId, userId));
    }
    @DeleteMapping("/users/{userId}/reminders/{reminderId}")
    public ResponseEntity<?> deleteReminder(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "reminderId") Long reminderId) {
        return reminderService.deleteReminder(reminderId, userId);
    }
    private Reminder convertToEntity(ReminderResource resource) {
        return mapper.map(resource, Reminder.class);
    }

    private ReminderResource convertToResource(Reminder entity) {
        return mapper.map(entity, ReminderResource.class);
    }
}
