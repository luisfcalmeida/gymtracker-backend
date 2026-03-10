package com.gymtracker.backend.service;

import com.gymtracker.backend.entity.WorkoutSet;
import com.gymtracker.backend.repository.WorkoutSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSetService {

    private final WorkoutSetRepository workoutSetRepository;

    public WorkoutSetService(WorkoutSetRepository workoutSetRepository) {
        this.workoutSetRepository = workoutSetRepository;
    }

    public List<WorkoutSet> findByWorkoutExercise(Long workoutExerciseId) {
        return workoutSetRepository.findByWorkoutExerciseIdOrderBySetNumber(workoutExerciseId);
    }

    public WorkoutSet save(WorkoutSet set) {
        return workoutSetRepository.save(set);
    }

    public void delete(Long id) {
        workoutSetRepository.deleteById(id);
    }

    public void deleteAllByWorkoutExercise(Long workoutExerciseId) {
        workoutSetRepository.deleteByWorkoutExerciseId(workoutExerciseId);
    }

}
