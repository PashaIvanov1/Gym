package com.ivanov.gym.controller;

import com.ivanov.gym.dto.EmployeeDTO;
import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Employee;
import com.ivanov.gym.model.Gym;
import com.ivanov.gym.repository.EmployeeRepository;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.EmployeeService;
import com.ivanov.gym.service.GymService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private GymService gymService;

    @MockBean
    private GymRepository gymRepository;

    @Test
    public void testShowAddEmployeeForm() throws Exception {
        List<GymDTO> gyms = new ArrayList<>();


        given(gymService.getAllGyms()).willReturn(gyms);

        mockMvc.perform(get("/employee/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addEmployee"))
                .andExpect(model().attributeExists("employeeDTO"))
                .andExpect(model().attribute("gyms", gyms));
    }

    @Test
    public void testSaveEmployee_InvalidEmployee_ReturnsAddEmployeeFormWithErrors() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("D");
        employeeDTO.setAge(15);
        employeeDTO.setDayOfBirth("01.01.199");
        employeeDTO.setPhone("38097567");
        employeeDTO.setPosition("Manager");
        employeeDTO.setEmail("johndoeexample.com");
        employeeDTO.setExperience(0);
        employeeDTO.setSalary(1500.0);
        employeeDTO.setGymId(new Gym());

        mockMvc.perform(post("/employee/add")
                        .flashAttr("employeeDTO", employeeDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("addEmployee"))
                .andExpect(model().attributeHasErrors("employeeDTO"));
    }

    @Test
    public void testUpdateEmployee_ValidEmployee_RedirectsToEmployeeAll() throws Exception {
        long id = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setAge(25);
        employeeDTO.setDayOfBirth("01.01.1998");
        employeeDTO.setPhone("380971234567");
        employeeDTO.setPosition("Manager");
        employeeDTO.setEmail("johndoe@example.com");
        employeeDTO.setExperience(3);
        employeeDTO.setSalary(1500.0);
        employeeDTO.setGymId(new Gym());

        Optional<Employee> employeeOptional = Optional.of(new Employee());
        given(employeeRepository.findById(id)).willReturn(employeeOptional);
        given(gymRepository.findById(employeeDTO.getGymId().getId())).willReturn(Optional.of(new Gym()));

        mockMvc.perform(post("/employee/update/{id}", id)
                        .flashAttr("employeeDTO", employeeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/all"));
    }

    @Test
    public void testUpdateEmployee_InvalidEmployee_ReturnsUpdateEmployeeFormWithErrors() throws Exception {
        long id = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("J");
        employeeDTO.setLastName("D");
        employeeDTO.setAge(2);
        employeeDTO.setDayOfBirth("01.01.199");
        employeeDTO.setPhone("381234567");
        employeeDTO.setPosition("Manager");
        employeeDTO.setEmail("johnexample.com");
        employeeDTO.setExperience(0);
        employeeDTO.setSalary(1500.0);
        employeeDTO.setGymId(new Gym());

        Optional<Employee> employeeOptional = Optional.of(new Employee());
        given(employeeRepository.findById(id)).willReturn(employeeOptional);
        given(gymRepository.findById(employeeDTO.getGymId().getId())).willReturn(Optional.of(new Gym()));

        mockMvc.perform(post("/employee/update/{id}", id)
                        .flashAttr("employeeDTO", employeeDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("update_employee"))
                .andExpect(model().attributeHasErrors("employeeDTO"));
    }

    @Test
    public void testDeleteEmployee_ValidId_RedirectsToEmployeeAll() throws Exception {

        long id = 1L;


        mockMvc.perform(get("/employee/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/all"));

        verify(employeeService, times(1)).deleteById(id);
    }

}
