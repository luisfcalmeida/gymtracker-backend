package com.gymtracker.backend.repository;

import com.gymtracker.backend.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    List<WorkoutPlan> findByNameIgnoreCase(String name);

    List<WorkoutPlan> findByUserId(Long userId);

    List<WorkoutPlan> findByUserIdAndNameIgnoreCase(Long userId, String name);

    List<WorkoutPlan> findByUserIdOrderByCreatedAtDesc(Long userId);

}
