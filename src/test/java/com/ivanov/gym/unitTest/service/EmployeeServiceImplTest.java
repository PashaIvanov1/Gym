package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.EmployeeDTO;
import com.ivanov.gym.exception.SalaryIncreaseException;
import com.ivanov.gym.model.Employee;
import com.ivanov.gym.repository.EmployeeRepository;
import com.ivanov.gym.service.impl.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void testDeleteById() {
        long employeeId = 1L;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(Mockito.mock(Employee.class)));

        employeeService.deleteById(employeeId);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(employeeId);
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);
    }

    @Test(expected = SalaryIncreaseException.class)
    public void testIncreaseSalaries_NotScheduledDay() {
        double amount = 100.0;
        LocalDate currentDate = LocalDate.of(2023, 6, 22);

        employeeService.increaseSalaries(amount, currentDate);
    }

    @Test
    public void testEmployeeExists() {
        String email = "john.doe@example.com";

        Mockito.when(employeeRepository.existsByEmail(email))
                .thenReturn(true);

        boolean result = employeeService.employeeExists(email);

        Mockito.verify(employeeRepository, Mockito.times(1))
                .existsByEmail(email);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetEmployeeDTOById() {
        long employeeId = 1L;
        Employee existingEmployee = Mockito.mock(Employee.class);
        EmployeeDTO employeeDTO = Mockito.mock(EmployeeDTO.class);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(modelMapper.map(existingEmployee, EmployeeDTO.class)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.getEmployeeDTOById(employeeId);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(employeeId);
        Mockito.verify(modelMapper, Mockito.times(1)).map(existingEmployee, EmployeeDTO.class);
        Assert.assertEquals(employeeDTO, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeDTOById_EmployeeNotFound() {
        long employeeId = 1L;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        employeeService.getEmployeeDTOById(employeeId);
    }
}
