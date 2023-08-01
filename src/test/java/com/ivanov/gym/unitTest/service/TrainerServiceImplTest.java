package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.TrainerDTO;
import com.ivanov.gym.model.Trainer;
import com.ivanov.gym.repository.TrainerRepository;
import com.ivanov.gym.service.impl.TrainerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test(expected = IllegalArgumentException.class)
    public void testAddTrainerWithExistingTrainer() {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setEmail("john.doe@example.com");

        Mockito.when(trainerService.trainerExists(trainerDTO.getEmail()))
                .thenReturn(true);

        trainerService.addTrainer(trainerDTO);
    }

    @Test
    public void testGetTrainerById() {
        long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        TrainerDTO expectedTrainerDTO = new TrainerDTO();
        expectedTrainerDTO.setId(trainerId);

        Mockito.when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        Mockito.when(modelMapper.map(trainer, TrainerDTO.class)).thenReturn(expectedTrainerDTO);

        TrainerDTO result = trainerService.getTrainerById(trainerId);

        Assert.assertEquals(expectedTrainerDTO, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTrainerByIdWithNonExistingTrainer() {
        long trainerId = 1L;

        Mockito.when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        trainerService.getTrainerById(trainerId);
    }

    @Test
    public void testGetAllTrainers() {
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        List<TrainerDTO> expectedTrainerDTOs = Arrays.asList(new TrainerDTO(), new TrainerDTO());

        Mockito.when(trainerRepository.findAll()).thenReturn(trainers);
        Mockito.when(modelMapper.map(Mockito.any(Trainer.class), Mockito.eq(TrainerDTO.class)))
                .thenReturn(expectedTrainerDTOs.get(0), expectedTrainerDTOs.get(1));

        List<TrainerDTO> result = trainerService.getAllTrainers();

        Assert.assertEquals(expectedTrainerDTOs, result);
    }

    @Test
    public void testUpdateTrainer() {
        long trainerId = 1L;
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstName("John");
        trainerDTO.setLastName("Doe");
        trainerDTO.setEmail("john.doe@example.com");

        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(trainerId);

        Mockito.when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(existingTrainer));

        trainerService.updateTrainer(trainerId, trainerDTO);

        Mockito.verify(modelMapper, Mockito.times(1)).map(trainerDTO, existingTrainer);
        Mockito.verify(trainerRepository, Mockito.times(1)).save(existingTrainer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTrainerWithNonExistingTrainer() {
        long trainerId = 1L;
        TrainerDTO trainerDTO = new TrainerDTO();

        Mockito.when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        trainerService.updateTrainer(trainerId, trainerDTO);
    }

    @Test
    public void testTrainerExists() {
        String email = "john.doe@example.com";

        Mockito.when(trainerRepository.existsByEmail(email))
                .thenReturn(true);

        boolean result = trainerService.trainerExists(email);

        Assert.assertTrue(result);
    }
}
