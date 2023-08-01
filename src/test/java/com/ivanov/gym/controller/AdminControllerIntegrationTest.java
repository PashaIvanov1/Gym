package com.ivanov.gym.controller;

import com.ivanov.gym.model.Client;
import com.ivanov.gym.model.Gym;
import com.ivanov.gym.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetClientAll() throws Exception {
        List<Client> clients = new ArrayList<>();
        clients.add(createClient("John", "Doe", 25, "01.01.1998", "380971234567", "john.doe@example.com", new Gym()));
        clients.add(createClient("Jane", "Smith", 30, "05.06.1991", "380981234567", "jane.smith@example.com", new Gym()));

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        mockMvc.perform(get("/admin/client/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("showClients"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attribute("clients", clients));
    }

    private Client createClient(String firstName, String lastName, int age, String dayOfBirth, String phone, String email, Gym gym) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        client.setDayOfBirth(dayOfBirth);
        client.setPhone(phone);
        client.setEmail(email);
        client.setGym(gym);
        return client;
    }
}
