package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.ExerciseDTO;
import com.ivanov.gym.service.ExerciseInitializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExerciseInitializerImpl implements ExerciseInitializer {

    @Override
    public List<ExerciseDTO> initialize() {
        List<ExerciseDTO> exerciseDTOs = new ArrayList<>();
        exerciseDTOs.add(new ExerciseDTO("Shoulders together", "Shoulders", "Beginner"));
        exerciseDTOs.add(new ExerciseDTO("Dumbbell Press", "Shoulders", "Intermediate"));
        exerciseDTOs.add(new ExerciseDTO("Shoulder spread", "Shoulders", "Advanced"));

        exerciseDTOs.add(new ExerciseDTO("Push-ups", "Chest", "Beginner"));
        exerciseDTOs.add(new ExerciseDTO("Bench Press", "Chest", "Intermediate"));
        exerciseDTOs.add(new ExerciseDTO("Dumbbell bench press", "Chest", "Advanced"));

        exerciseDTOs.add(new ExerciseDTO("Dumbbell lifting", "Biceps", "Beginner"));
        exerciseDTOs.add(new ExerciseDTO("Barbell Curls", "Biceps", "Intermediate"));
        exerciseDTOs.add(new ExerciseDTO("Dumbbell head press", "Biceps", "Advanced"));

        exerciseDTOs.add(new ExerciseDTO("Leg raise", "Abs", "Beginner"));
        exerciseDTOs.add(new ExerciseDTO("Plank", "Abs", "Intermediate"));
        exerciseDTOs.add(new ExerciseDTO("Twisting", "Abs", "Advanced"));
        return exerciseDTOs;
    }
}
