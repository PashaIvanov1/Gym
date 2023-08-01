package com.ivanov.gym.dto;

import com.ivanov.gym.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {

    private Long id;

    @NotEmpty(message = "Exercise Name should not be empty")
    @Size(min = 2, max = 50, message = "Exercise Name should be between 2 and 50 characters")
    private String exerciseName;

    @NotEmpty(message = "Muscle Group should not be empty")
    @Size(min = 2, max = 50, message = "Muscle Group should be between 2 and 50 characters")
    private String muscleGroup;

    @NotEmpty(message = "Difficulty Level should not be empty")
    @Size(min = 2, max = 20, message = "Difficulty Level should be between 2 and 20 characters")
    private String difficultyLevel;

    public ExerciseDTO(String exerciseName, String muscleGroup, String difficultyLevel) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.difficultyLevel = difficultyLevel;
    }

    public ExerciseDTO(Exercise exercise) {
        this.id = exercise.getId();
        this.exerciseName = exercise.getExerciseName();
        this.muscleGroup = exercise.getMuscleGroup();
        this.difficultyLevel = exercise.getDifficultyLevel();
    }
}
