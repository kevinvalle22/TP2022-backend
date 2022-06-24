package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.Goal;
import com.itconsulting.mentalHealth.core.repository.GoalRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.GoalService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Page<Goal> getAllGoals(Pageable pageable) {
        return goalRepository.findAll(pageable);
    }

    @Override
    public Page<Goal> getAllGoalsByUserId(Long userId, Pageable pageable) {
        return goalRepository.findByUserId(userId,pageable);
    }

    @Override
    public Goal getGoalByIdAndUserId(Long goalId, Long userId) {
        return goalRepository.findByIdAndUserId(goalId,userId).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found goal Id with Id "+ goalId+" and userId "+ userId));
    }

    @Override
    public Goal updateGoalById(Goal goal, Long goalId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Goal goal1= goalRepository.findById(goalId).orElseThrow(()-> new ResourceNotFoundException("Goal", "Id", goalId));
        goal1.setType(goal.getType());
        goal1.setStartDate(goal.getStartDate());
        goal1.setMessage(goal.getMessage());
        goal1.setStatus(goal.getStatus());
        goal1.setActionPlan1(goal.getActionPlan1());
        goal1.setActionPlan2(goal.getActionPlan2());
        goal1.setActionPlan2(goal.getActionPlan3());

        return goalRepository.save(goal1);
    }

    @Override
    public Goal saveGoal(Goal goal, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    @Override
    public ResponseEntity<?> deleteGoal(Long goalId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Goal goal1= goalRepository.findById(goalId).orElseThrow(()-> new ResourceNotFoundException("Goal", "Id", goalId));
        goalRepository.delete(goal1);
        return ResponseEntity.ok().build();
    }
}
