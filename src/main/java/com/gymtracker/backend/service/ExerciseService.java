package com.gymtracker.backend.service;

import com.gymtracker.backend.entity.Exercise;
import com.gymtracker.backend.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    public List<Exercise> findByName(String name) {
        return exerciseRepository.findByNameIgnoreCase(name);
    }

    public List<Exercise> findByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByMuscleGroupIgnoreCase(muscleGroup);
    }

    public List<Exercise> findByEquipment(String equipment) {
        return exerciseRepository.findByEquipmentIgnoreCase(equipment);
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }

}
