package com.gymtracker.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "workout_sets")
public class WorkoutSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    @Column(name = "weight", precision = 6, scale = 2)
    private BigDecimal weight;

    @Column(name = "rest_seconds")
    private Integer restSeconds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public Integer getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(Integer restSeconds) {
        this.restSeconds = restSeconds;
    }
}
