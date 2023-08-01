package com.ivanov.gym.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void testReturnOne() throws Exception {
        mockMvc.perform(get("/user/one"))
                .andExpect(status().isOk())
                .andExpect(view().name("program_one"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void testReturnTwo() throws Exception {
        mockMvc.perform(get("/user/two"))
                .andExpect(status().isOk())
                .andExpect(view().name("program_two"));

    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void testReturnThree() throws Exception {
        mockMvc.perform(get("/user/three"))
                .andExpect(status().isOk())
                .andExpect(view().name("program_three"));

    }
}
