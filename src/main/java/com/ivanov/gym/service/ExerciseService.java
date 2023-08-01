package com.ivanov.gym.service;

import com.ivanov.gym.dto.ExerciseDTO;

import java.util.List;

public interface ExerciseService {

    void addExercise(ExerciseDTO exerciseDTO);

    List<ExerciseDTO> getExercisesByMuscleGroupAndDifficulty(String muscleGroup, String difficulty);

    List<ExerciseDTO> initialize();
}
