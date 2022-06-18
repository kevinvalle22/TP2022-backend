package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.SleepRecord;
import com.itconsulting.mentalHealth.core.service.SleepRecordService;
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
public class SleepRecordController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SleepRecordService sleepRecordService;
    @GetMapping(value = "/sleeps")
    public Page<SleepRecordResource> getAllSleepRecords(Pageable pageable) {
        Page<SleepRecord> sleepRecords = sleepRecordService.getAllSleepRecords(pageable);
        List<SleepRecordResource> resources = sleepRecords.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/sleeps")
    public Page<SleepRecordResource> getAllSleepRecordsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<SleepRecordResource> sleepRecordResources = sleepRecordService.getAllSleepRecordsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = sleepRecordResources.size();
        return new PageImpl<>(sleepRecordResources, pageable, count);
    }

    @GetMapping("/users/{userId}/sleeps/{sleepId}")
    public SleepRecordResource getSleepRecordByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                         @PathVariable(name = "sleepId") Long sleepId) {
        return convertToResource(sleepRecordService.getSleepRecordByIdAndUserId(sleepId, userId));
    }
    @PostMapping("/users/{userId}/sleeps")
    public SleepRecordResource createSleep(@PathVariable(name = "userId") Long userId,
                                         @Valid @RequestBody SleepRecordResource resource) {
        Long sleepRecordId=  sleepRecordService.saveSleepRecord(convertToEntity(resource),userId).getId();
        return  convertToResource(sleepRecordService.getTheDayOfWeek(sleepRecordId,userId));

    }
    @PutMapping("/users/{userId}/sleeps/{sleepId}")
    public SleepRecordResource updateTestResult(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "sleepId") Long sleepId,
                                               @Valid @RequestBody SleepRecordResource resource) {
        return convertToResource(sleepRecordService.updateSleepRecordById(convertToEntity(resource),sleepId, userId));
    }
    @DeleteMapping("/users/{userId}/sleeps/{sleepId}")
    public ResponseEntity<?> deleteSleepRecord(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "sleepId") Long sleepId) {
        return sleepRecordService.deleteSleepRecord(sleepId, userId);
    }
    private SleepRecord convertToEntity(SleepRecordResource resource) {
        return mapper.map(resource, SleepRecord.class);
    }

    private SleepRecordResource convertToResource(SleepRecord entity) {
        return mapper.map(entity, SleepRecordResource.class);
    }
}
