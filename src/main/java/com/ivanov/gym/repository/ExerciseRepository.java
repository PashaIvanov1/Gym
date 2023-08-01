package com.ivanov.gym.repository;

import com.ivanov.gym.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroupAndDifficultyLevel(String muscleGroup, String difficultyLevel);
}
