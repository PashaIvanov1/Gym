package com.ivanov.gym.service;

import com.ivanov.gym.dto.EmployeeDTO;
import com.ivanov.gym.model.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAll();

    EmployeeDTO addEmployee(EmployeeDTO employee);

    EmployeeDTO updateEmployee(long id, EmployeeDTO employeeDTO);

    boolean employeeExists(String email);

    void deleteById(long id);

    void increaseSalaries(double amount, LocalDate currentDate);

    EmployeeDTO getEmployeeDTOById(long id);

    Employee getExistingEmployee(long id);

    void validateGymId(long gymId);

}
