package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.Goal;
import com.itconsulting.mentalHealth.core.entity.Goal;
import com.itconsulting.mentalHealth.core.service.GoalService;
import com.itconsulting.mentalHealth.resource.GoalResource;
import com.itconsulting.mentalHealth.resource.GoalResource;
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
public class GoalController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private GoalService goalService;

    @GetMapping(value = "/goals")
    public Page<GoalResource> getAllGoals(Pageable pageable) {
        Page<Goal> goals = goalService.getAllGoals(pageable);
        List<GoalResource> resources = goals.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/goals")
    public Page<GoalResource> getAllGoalsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<GoalResource> resources = goalService.getAllGoalsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/goals/{goalId}")
    public GoalResource getGoalByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                           @PathVariable(name = "goalId") Long goalId) {
        return convertToResource(goalService.getGoalByIdAndUserId(goalId, userId));
    }

    @PostMapping("/users/{userId}/goals")
    public GoalResource createGoal(@PathVariable(name = "userId") Long userId,
                                                 @Valid @RequestBody GoalResource resource) {
        return convertToResource(goalService.saveGoal(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/goals/{goalId}")
    public GoalResource updateGoal(@PathVariable(name = "userId") Long userId,
                                                 @PathVariable(name = "goalId") Long goalId,
                                                 @Valid @RequestBody GoalResource resource) {
        return convertToResource(goalService.updateGoalById(convertToEntity(resource),goalId, userId));
    }
    @DeleteMapping("/users/{userId}/goals/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "goalId") Long goalId) {
        return goalService.deleteGoal(goalId, userId);
    }
    private Goal convertToEntity(GoalResource resource) {
        return mapper.map(resource, Goal.class);
    }

    private GoalResource convertToResource(Goal entity) {
        return mapper.map(entity, GoalResource.class);
    }
}
