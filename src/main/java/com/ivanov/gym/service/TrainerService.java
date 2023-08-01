package com.ivanov.gym.service;

import com.ivanov.gym.dto.TrainerDTO;
import com.ivanov.gym.model.Trainer;

import java.util.List;

public interface TrainerService {

    void addTrainer(TrainerDTO trainerDTO);

    TrainerDTO getTrainerById(long id);

    List<TrainerDTO> getAllTrainers();

    void updateTrainer(long trainerId, TrainerDTO trainerDTO);

    boolean trainerExists(String email);

    void deleteTrainer(long trainerId);
    Trainer getTrainerByIdInternal(long id);

}
