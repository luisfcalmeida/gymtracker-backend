package com.gymtracker.backend.repository;

import com.gymtracker.backend.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByNameIgnoreCase(String name);

    List<Exercise> findByMuscleGroupIgnoreCase(String muscleGroup);

    List<Exercise> findByEquipmentIgnoreCase(String equipment);

    List<Exercise> findByMuscleGroupIgnoreCaseAndEquipmentIgnoreCase(String muscleGroup, String equipment);

}
