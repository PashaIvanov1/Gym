package com.ivanov.gym.service;

import com.ivanov.gym.dto.ExerciseDTO;

import java.util.List;

public interface ExerciseInitializer {

    List<ExerciseDTO> initialize();
}
