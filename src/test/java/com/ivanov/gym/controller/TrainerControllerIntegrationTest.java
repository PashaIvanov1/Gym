package com.ivanov.gym.controller;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.dto.TrainerDTO;
import com.ivanov.gym.service.GymService;
import com.ivanov.gym.service.TrainerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", roles = "ADMIN")
@RunWith(SpringRunner.class)
@WebMvcTest(TrainerController.class)
public class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @MockBean
    private GymService gymService;

    @Test
    public void testShowUpdateForm_WithExistingTrainerId_ReturnsUpdateTrainerView() throws Exception {
        long trainerId = 1L;
        TrainerDTO trainerDTO = new TrainerDTO();
        List<GymDTO> gyms = Arrays.asList(new GymDTO(), new GymDTO());

        given(trainerService.getTrainerById(trainerId)).willReturn(trainerDTO);
        given(gymService.getAllGyms()).willReturn(gyms);

        mockMvc.perform(get("/trainer/update/{id}", trainerId))
                .andExpect(status().isOk())
                .andExpect(view().name("update_trainer"))
                .andExpect(model().attributeExists("gyms"))
                .andExpect(model().attribute("gyms", gyms))
                .andExpect(model().attributeExists("trainerDTO"))
                .andExpect(model().attribute("trainerDTO", trainerDTO));

        verify(trainerService, times(1)).getTrainerById(trainerId);
        verify(gymService, times(1)).getAllGyms();
    }

    @Test
    public void testShowUpdateForm_WithNonExistingTrainerId_RedirectsToAllTrainers() throws Exception {
        long trainerId = 1L;

        given(trainerService.getTrainerById(trainerId)).willReturn(null);

        mockMvc.perform(get("/trainer/update/{id}", trainerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainer/all"));

        verify(trainerService, times(1)).getTrainerById(trainerId);
    }

    @Test
    public void testDeleteTrainer_WithExistingTrainerId_RedirectsToAllTrainers() throws Exception {
        long trainerId = 1L;

        mockMvc.perform(get("/trainer/delete/{id}", trainerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainer/all"));

        verify(trainerService, times(1)).deleteTrainer(trainerId);
    }

    @Test
    public void testShowAddTrainerForm() throws Exception {
        List<GymDTO> gyms = Arrays.asList(new GymDTO(), new GymDTO());
        given(gymService.getAllGyms()).willReturn(gyms);

        mockMvc.perform(get("/trainer/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addTrainer"))
                .andExpect(model().attributeExists("gyms", "trainerDTO"))
                .andExpect(model().attribute("gyms", gyms));
    }

    @Test
    public void testDeleteTrainer_TrainerExists_RedirectsToAllTrainers() throws Exception {
        mockMvc.perform(get("/trainer/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainer/all"));
    }
}
