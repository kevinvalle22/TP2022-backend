package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.MoodTracker;
import com.itconsulting.mentalHealth.core.service.MoodTrackerService;
import com.itconsulting.mentalHealth.resource.MoodTrackerResource;
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
public class MoodTrackerController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MoodTrackerService moodTrackerService;


    @GetMapping(value = "/moods")
    public Page<MoodTrackerResource> getAllMoodTrackers(Pageable pageable) {
        Page<MoodTracker> moodTrackers = moodTrackerService.getAllMoodTrackers(pageable);
        List<MoodTrackerResource> resources = moodTrackers.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/moods")
    public Page<MoodTrackerResource> getAllMoodTrackersByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<MoodTrackerResource> resources = moodTrackerService.getAllMoodTrackersByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/moods/{moodId}")
    public MoodTrackerResource getMoodTrackerByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                     @PathVariable(name = "moodId") Long moodId) {
        return convertToResource(moodTrackerService.getMoodTrackerByIdAndUserId(moodId, userId));
    }


    @PostMapping("/users/{userId}/moods")
    public MoodTrackerResource createMoodTracker(@PathVariable(name = "userId") Long userId,
                                           @Valid @RequestBody MoodTrackerResource resource) {
        return convertToResource(moodTrackerService.saveMoodTracker(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/moods/{moodId}")
    public MoodTrackerResource updateMoodTracker(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "moodId") Long moodId,
                                           @Valid @RequestBody MoodTrackerResource resource) {
        return convertToResource(moodTrackerService.updateMoodTrackerById(convertToEntity(resource),moodId, userId));
    }
    @DeleteMapping("/users/{userId}/moods/{moodId}")
    public ResponseEntity<?> deleteMoodTracker(@PathVariable(name = "userId") Long userId,
                                            @PathVariable(name = "moodId") Long moodId) {
        return moodTrackerService.deleteMoodTracker(moodId, userId);
    }
    private MoodTracker convertToEntity(MoodTrackerResource resource) {
        return mapper.map(resource, MoodTracker.class);
    }

    private MoodTrackerResource convertToResource(MoodTracker entity) {
        return mapper.map(entity, MoodTrackerResource.class);
    }
}
