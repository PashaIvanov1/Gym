package com.ivanov.gym.model;

import com.ivanov.gym.dto.ExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Exercise Name should not be empty")
    @Size(min = 2, max = 50, message = "Exercise Name should be between 2 and 50 characters")
    @Column(name = "exercise_name")
    private String exerciseName;

    @NotEmpty(message = "Muscle Group should not be empty")
    @Size(min = 2, max = 50, message = "Muscle Group should be between 2 and 50 characters")
    @Column(name = "muscle_group")
    private String muscleGroup;

    @NotEmpty(message = "Difficulty Level should not be empty")
    @Size(min = 2, max = 20, message = "Difficulty Level should be between 2 and 20 characters")
    @Column(name = "difficulty_level")
    private String difficultyLevel;

    public Exercise(ExerciseDTO exerciseDTO) {
        this.exerciseName = exerciseDTO.getExerciseName();
        this.muscleGroup = exerciseDTO.getMuscleGroup();
        this.difficultyLevel = exerciseDTO.getDifficultyLevel();
    }
}
