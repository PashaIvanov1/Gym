package com.ivanov.gym.controller;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.GymService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GymIntegrationTest {

    @Mock
    private GymService gymService;

    @InjectMocks
    private GymController gymController;

    @Test
    public void testShowGymAll() {
        Model model = new ExtendedModelMap();
        List<GymDTO> gyms = new ArrayList<>();
        gyms.add(new GymDTO());
        gyms.add(new GymDTO());

        Mockito.when(gymService.getAllGyms()).thenReturn(gyms);

        String viewName = gymController.showGymAll(model);

        assertEquals("showGym", viewName);
        assertTrue(model.containsAttribute("gyms"));

        List<GymDTO> actualGyms = (List<GymDTO>) model.getAttribute("gyms");
        assertEquals(gyms.size(), actualGyms.size());
    }

    @Test
    public void testSaveGym() {
        Model model = new ExtendedModelMap();
        GymDTO gymDTO = new GymDTO();
        gymDTO.setCity("Test City");
        gymDTO.setStreet("Test Street");

        String viewName = gymController.saveGym(gymDTO, new BeanPropertyBindingResult(gymDTO, "gymDTO"), model);

        assertEquals("redirect:/gym/all", viewName);
        assertFalse(model.containsAttribute("gymDTO"));

        Mockito.verify(gymService, Mockito.times(1)).saveGym(gymDTO);
    }

}

