package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface GoalService {
    Page<Goal> getAllGoals(Pageable pageable);
    Page<Goal> getAllGoalsByUserId(Long userId, Pageable pageable);


    Goal getGoalByIdAndUserId(Long goalId,Long userId);
    Goal updateGoalById(Goal goal,Long goalId, Long userId);
    Goal saveGoal(Goal goal, Long userId);

    ResponseEntity<?> deleteGoal(Long goalId, Long userId);
}
