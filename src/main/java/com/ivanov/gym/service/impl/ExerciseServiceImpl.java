package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.ExerciseDTO;
import com.ivanov.gym.model.Exercise;
import com.ivanov.gym.repository.ExerciseRepository;
import com.ivanov.gym.service.ExerciseInitializer;
import com.ivanov.gym.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger log = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;
    private final ExerciseInitializer exerciseInitializer;

    @Override
    public void addExercise(ExerciseDTO exerciseDTO) {
        log.info("Adding exercise: {}", exerciseDTO);
        if (exerciseDTO == null || exerciseDTO.getExerciseName() == null || exerciseDTO.getMuscleGroup() == null || exerciseDTO.getDifficultyLevel() == null) {
            throw new IllegalArgumentException("Invalid exerciseDTO");
        }
        Exercise exercise = new Exercise(exerciseDTO);
        exerciseRepository.save(exercise);
        log.info("Exercise added successfully");
    }

    @Override
    public List<ExerciseDTO> getExercisesByMuscleGroupAndDifficulty(String muscleGroup, String difficulty) {
        log.info("Getting exercises by muscle group: {} and difficulty: {}", muscleGroup, difficulty);
        if (muscleGroup == null || difficulty == null) {
            throw new IllegalArgumentException("Invalid muscle group or difficulty");
        }
        List<Exercise> exercises = exerciseRepository.findByMuscleGroupAndDifficultyLevel(muscleGroup, difficulty);
        List<ExerciseDTO> exerciseDTOs = exercises.stream()
                .map(ExerciseDTO::new)
                .collect(Collectors.toList());
        log.info("Found {} exercises", exerciseDTOs.size());
        return exerciseDTOs;
    }

    @Override
    public List<ExerciseDTO> initialize() {
        log.info("Initializing exercises");
        List<ExerciseDTO> exerciseDTOs = exerciseInitializer.initialize();
        for (ExerciseDTO exerciseDTO : exerciseDTOs) {
            Exercise exercise = new Exercise(exerciseDTO);
            exerciseRepository.save(exercise);
        }
        log.info("Exercises initialized successfully");
        return exerciseDTOs;
    }
}
