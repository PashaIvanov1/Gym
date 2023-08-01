package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.ExerciseDTO;
import com.ivanov.gym.service.impl.ExerciseServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceImplTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExerciseWithInvalidExerciseDTO() {
        ExerciseDTO exerciseDTO = new ExerciseDTO();

        exerciseService.addExercise(exerciseDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExercisesByMuscleGroupAndDifficultyWithInvalidParameters() {
        exerciseService.getExercisesByMuscleGroupAndDifficulty(null, "Beginner");
    }
}
