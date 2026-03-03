package com.gymtracker.backend.repository;

import com.gymtracker.backend.entity.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {

    List<WorkoutSet> findByWorkoutExerciseIdOrderBySetNumber(Long workoutExerciseId);

    void deleteByWorkoutExerciseId(Long workoutExerciseId);

    List<WorkoutSet> findByWorkoutExerciseIdAndRepetitions(Long workoutExerciseId, Integer repetitions);

    Optional<WorkoutSet> findByWorkoutExerciseIdAndSetNumber(Long workoutExerciseId, Integer setNumber);

}
