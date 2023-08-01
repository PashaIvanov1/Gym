package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.TrainerDTO;
import com.ivanov.gym.model.Trainer;
import com.ivanov.gym.repository.TrainerRepository;
import com.ivanov.gym.service.TrainerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private static final Logger log = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addTrainer(TrainerDTO trainerDTO) {
        log.info("Adding trainer: {}", trainerDTO);
        String email = trainerDTO.getEmail();

        if (trainerExists(email)) {
            log.info("Trainer with the specified email already exists: {}", trainerDTO);
            throw new IllegalArgumentException("Trainer with the specified email already exists");
        }

        Trainer trainer = modelMapper.map(trainerDTO, Trainer.class);
        trainerRepository.save(trainer);
        log.info("Trainer added successfully");
    }

    @Override
    public TrainerDTO getTrainerById(long id) {
        log.info("Getting trainer by ID: {}", id);
        Trainer trainer = getTrainerByIdInternal(id);

        TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
        log.info("Found trainer: {}", trainerDTO);
        return trainerDTO;
    }

    @Override
    public List<TrainerDTO> getAllTrainers() {
        log.info("Getting all trainers");
        List<Trainer> trainers = trainerRepository.findAll();

        List<TrainerDTO> trainerDTOs = trainers.stream()
                .map(trainer -> modelMapper.map(trainer, TrainerDTO.class))
                .collect(Collectors.toList());
        log.info("Found {} trainers", trainerDTOs.size());
        return trainerDTOs;
    }

    @Override
    public void updateTrainer(long trainerId, TrainerDTO trainerDTO) {
        log.info("Updating trainer with ID: {}", trainerId);
        Trainer trainer = getTrainerByIdInternal(trainerId);
        modelMapper.map(trainerDTO, trainer);
        trainer.setId(trainerId);
        trainerRepository.save(trainer);
        log.info("Trainer updated successfully");
    }

    @Override
    public boolean trainerExists(String email) {
        log.info("Checking if trainer exists with email: {}", email);
        boolean exists = trainerRepository.existsByEmail(email);
        if (exists) {
            log.info("Trainer exists");
        } else {
            log.info("Trainer does not exist");
        }
        return exists;
    }

    @Override
    public void deleteTrainer(long trainerId) {
        log.info("Deleting trainer with ID: {}", trainerId);
        Trainer trainer = getTrainerByIdInternal(trainerId);
        trainerRepository.delete(trainer);
        log.info("Trainer deleted successfully");
    }
    @Override
    public Trainer getTrainerByIdInternal(long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer with the specified ID does not exist"));
    }
}
