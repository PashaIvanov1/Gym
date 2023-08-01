package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Gym;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.impl.GymServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class GymServiceImplTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private GymServiceImpl gymService;

    @Test
    public void testGetAllGyms() {
        Gym gym1 = new Gym();
        Gym gym2 = new Gym();
        List<Gym> gyms = Arrays.asList(gym1, gym2);

        Mockito.when(gymRepository.findAll()).thenReturn(gyms);

        List<GymDTO> result = gymService.getAllGyms();

        Assert.assertEquals(gyms.size(), result.size());
    }

    @Test
    public void testSaveGym() {
        GymDTO gymDTO = new GymDTO();

        gymService.saveGym(gymDTO);

        Mockito.verify(gymRepository, Mockito.times(1)).save(Mockito.any(Gym.class));
    }

    @Test
    public void testUpdateGym() {
        long gymId = 1L;
        GymDTO gymDTO = new GymDTO();
        gymDTO.setId(gymId);

        Gym existingGym = new Gym();
        Optional<Gym> optionalGym = Optional.of(existingGym);

        Mockito.when(gymRepository.findById(gymId)).thenReturn(optionalGym);
        Mockito.when(gymRepository.save(Mockito.any(Gym.class))).thenReturn(existingGym);

        gymService.updateGym(gymDTO);

        Mockito.verify(gymRepository, Mockito.times(1)).save(existingGym);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGymWithNonExistingGym() {
        long gymId = 1L;
        GymDTO gymDTO = new GymDTO();
        gymDTO.setId(gymId);

        Mockito.when(gymRepository.findById(gymId)).thenReturn(Optional.empty());

        gymService.updateGym(gymDTO);
    }

    @Test
    public void testGetGymById() {
        long gymId = 1L;
        Gym gym = new Gym();
        Optional<Gym> optionalGym = Optional.of(gym);

        Mockito.when(gymRepository.findById(gymId)).thenReturn(optionalGym);

        GymDTO result = gymService.getGymById(gymId);

        Assert.assertEquals(gym.getId(), result.getId());
    }

    @Test
    public void testDeleteGymById() {
        long gymId = 1L;
        Gym gym = new Gym();
        Optional<Gym> optionalGym = Optional.of(gym);

        Mockito.when(gymRepository.findById(gymId)).thenReturn(optionalGym);

        gymService.deleteGymById(gymId);

        Mockito.verify(gymRepository, Mockito.times(1)).delete(gym);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteGymByIdWithNonExistingGym() {
        long gymId = 1L;

        Mockito.when(gymRepository.findById(gymId)).thenReturn(Optional.empty());

        gymService.deleteGymById(gymId);
    }
}
