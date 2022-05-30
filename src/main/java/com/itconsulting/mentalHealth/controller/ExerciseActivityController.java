package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.ExerciseActivity;
import com.itconsulting.mentalHealth.core.service.ExerciseActivityService;
import com.itconsulting.mentalHealth.resource.ExerciseActivityResource;
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
public class ExerciseActivityController {


    @Autowired
    private ModelMapper mapper;    
    @Autowired
    private ExerciseActivityService exerciseActivityService;


    @GetMapping(value = "/exercises")
    public Page<ExerciseActivityResource> getAllExerciseActivitys(Pageable pageable) {
        Page<ExerciseActivity> exercises = exerciseActivityService.getAllExerciseActivities(pageable);
        List<ExerciseActivityResource> resources = exercises.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/exercises")
    public Page<ExerciseActivityResource> getAllExerciseActivitysByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<ExerciseActivityResource> resources = exerciseActivityService.getAllExerciseActivitiesByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/exercises/{exerciseActivityId}")
    public ExerciseActivityResource getExerciseActivityByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "exerciseActivityId") Long exerciseActivityId) {
        return convertToResource(exerciseActivityService.getExerciseActivityByIdAndUserId(exerciseActivityId, userId));
    }

    @PostMapping("/users/{userId}/exercises")
    public ExerciseActivityResource createExerciseActivity(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody ExerciseActivityResource resource) {
        return convertToResource(exerciseActivityService.saveExerciseActivity(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/exercises/{exerciseActivityId}")
    public ExerciseActivityResource updateExerciseActivity(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "exerciseActivityId") Long exerciseActivityId,
                                   @Valid @RequestBody ExerciseActivityResource resource) {
        return convertToResource(exerciseActivityService.updateExerciseActivityById(convertToEntity(resource),exerciseActivityId, userId));
    }
    @DeleteMapping("/users/{userId}/exercises/{exerciseActivityId}")
    public ResponseEntity<?> deleteExerciseActivity(@PathVariable(name = "userId") Long userId,
                                        @PathVariable(name = "exerciseActivityId") Long exerciseActivityId) {
        return exerciseActivityService.deleteExerciseActivity(exerciseActivityId, userId);
    }

    private ExerciseActivity convertToEntity(ExerciseActivityResource resource) {
        return mapper.map(resource, ExerciseActivity.class);
    }

    private ExerciseActivityResource convertToResource(ExerciseActivity entity) {
        return mapper.map(entity, ExerciseActivityResource.class);
    }
}
