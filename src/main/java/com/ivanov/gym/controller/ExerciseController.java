package com.ivanov.gym.controller;

import com.ivanov.gym.dto.ExerciseDTO;
import com.ivanov.gym.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/exercises")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    private static final Logger log = LoggerFactory.getLogger(ExerciseController.class);

    @GetMapping()
    public String exercise() {
        return "exercise_body";
    }

    @PostMapping("/addExercise")
    public ResponseEntity<String> createExercise(@RequestBody @Valid ExerciseDTO exerciseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error exercise added");
        exerciseService.addExercise(exerciseDTO);
        log.info("Adding exercise: {}", exerciseDTO);
        return ResponseEntity.ok("Exercise added successfully");
    }

    @GetMapping("/")
    public ResponseEntity<List<ExerciseDTO>> getExercises(@RequestParam("muscleGroup") String muscleGroup,
                                                          @RequestParam("difficultyLevel") String difficultyLevel) {
        log.info("Getting exercises for muscle group: {}, difficulty level: {}", muscleGroup, difficultyLevel);
        List<ExerciseDTO> exercises = exerciseService.getExercisesByMuscleGroupAndDifficulty(muscleGroup, difficultyLevel);
        return ResponseEntity.ok(exercises);
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializePrograms() {
        log.info("Initializing exercises");
        exerciseService.initialize();
        return ResponseEntity.ok("Database initialized successfully");
    }
}
