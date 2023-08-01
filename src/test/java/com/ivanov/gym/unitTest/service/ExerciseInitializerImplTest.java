package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.ExerciseDTO;
import com.ivanov.gym.service.impl.ExerciseInitializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class ExerciseInitializerImplTest {

    @Test
    void initialize_ShouldReturnListOfExerciseDTOs() {
        ExerciseInitializerImpl initializer = new ExerciseInitializerImpl();

        List<ExerciseDTO> exerciseDTOs = initializer.initialize();

        Assertions.assertFalse(exerciseDTOs.isEmpty());
        Assertions.assertEquals(12, exerciseDTOs.size());
    }

    @Test
    void initialize_ShouldContainExerciseDTOsForDifferentMuscleGroups() {
        ExerciseInitializerImpl initializer = new ExerciseInitializerImpl();

        List<ExerciseDTO> exerciseDTOs = initializer.initialize();

        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getMuscleGroup().equals("Shoulders")));
        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getMuscleGroup().equals("Chest")));
        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getMuscleGroup().equals("Biceps")));
        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getMuscleGroup().equals("Abs")));
    }

    @Test
    void initialize_ShouldContainExerciseDTOsForDifferentLevels() {
        ExerciseInitializerImpl initializer = new ExerciseInitializerImpl();

        List<ExerciseDTO> exerciseDTOs = initializer.initialize();

        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getDifficultyLevel().equals("Beginner")));
        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getDifficultyLevel().equals("Intermediate")));
        Assertions.assertTrue(exerciseDTOs.stream().anyMatch(dto -> dto.getDifficultyLevel().equals("Advanced")));
    }
}
