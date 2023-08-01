package com.ivanov.gym.controller;

import com.ivanov.gym.dto.ClientDTO;
import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.service.ClientService;
import com.ivanov.gym.service.GymService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", roles = "ADMIN")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private GymService gymService;

    @Test
    public void testSaveClient_InvalidClient_ShowsAddClientFormWithErrors() throws Exception {
        mockMvc.perform(post("/client/add")
                        .param("email", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(view().name("addClient"))
                .andExpect(model().attributeExists("gyms"))
                .andExpect(model().attributeExists("clientDTO"))
                .andExpect(model().hasErrors());

        verifyNoInteractions(clientService);
    }

    @Test
    public void testShowUpdateForm_ValidId_ShowsUpdateFormWithClientDTO() throws Exception {
        long id = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(id);

        List<GymDTO> gyms = Arrays.asList(new GymDTO(), new GymDTO());

        given(clientService.getClientDTOById(id)).willReturn(clientDTO);
        given(gymService.getAllGyms()).willReturn(gyms);

        mockMvc.perform(get("/client/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("update_client"))
                .andExpect(model().attributeExists("gyms"))
                .andExpect(model().attributeExists("clientDTO"))
                .andExpect(model().attribute("clientDTO", clientDTO));

        verify(clientService).getClientDTOById(id);
        verify(gymService).getAllGyms();
    }

    @Test
    public void testUpdateClient_InvalidClient_ShowsUpdateFormWithErrors() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/client/update/{id}", id)
                        .param("email", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(view().name("update_client"))
                .andExpect(model().attributeExists("gyms"))
                .andExpect(model().attributeExists("clientDTO"))
                .andExpect(model().hasErrors());

        verifyNoInteractions(clientService);
    }

    @Test
    public void testDeleteClient_ValidId_RedirectsToAllClients() throws Exception {
        long id = 1L;

        mockMvc.perform(get("/client/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client/all"));

        verify(clientService).deleteById(id);
    }

    @Test
    public void testShowClientAll() throws Exception {
        mockMvc.perform(get("/client/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("showClients"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    public void testShowAddClientForm() throws Exception {
        mockMvc.perform(get("/client/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addClient"))
                .andExpect(model().attributeExists("clientDTO"))
                .andExpect(model().attributeExists("gyms"));
    }
}
