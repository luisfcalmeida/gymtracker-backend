package com.gymtracker.backend.service;

import com.gymtracker.backend.entity.WorkoutPlan;
import com.gymtracker.backend.repository.WorkoutPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    public Optional<WorkoutPlan> findById(Long id) {
        return workoutPlanRepository.findById(id);
    }

    public List<WorkoutPlan> findByUserId(Long userId) {
        return workoutPlanRepository.findByUserId(userId);
    }

    public List<WorkoutPlan> findByUserIdAndName(Long userId, String name) {
        return workoutPlanRepository.findByUserIdAndNameIgnoreCase(userId, name);
    }

    public WorkoutPlan save(WorkoutPlan plan) {
        return workoutPlanRepository.save(plan);
    }

    public void delete(Long id) {
        workoutPlanRepository.deleteById(id);
    }

}
