package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.EmployeeDTO;
import com.ivanov.gym.exception.SalaryIncreaseException;
import com.ivanov.gym.model.Employee;
import com.ivanov.gym.repository.EmployeeRepository;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.ivanov.gym.enums.Constants.DAY_OF_SALARY_INCREASE;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final GymRepository gymRepository;

    @Override
    public List<EmployeeDTO> getAll() {
        log.info("Getting all employees");
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        log.info("Adding employee: {}", employeeDTO);
        String email = employeeDTO.getEmail();

        if (employeeExists(email)) {
            log.info("Employee with the specified information already exists: {}",  employeeDTO);
            throw new IllegalArgumentException("Employee with the specified information already exists");
        }

        validateGymId(employeeDTO.getGymId().getId());

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee = employeeRepository.save(employee);
        log.info("Employee saved successfully");
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee by ID: {}, employeeDTO: {}", id, employeeDTO);
        Employee existingEmployee = getExistingEmployee(id);

        validateGymId(employeeDTO.getGymId().getId());

        modelMapper.map(employeeDTO, existingEmployee);
        existingEmployee = employeeRepository.save(existingEmployee);
        log.info("Employee updated successfully");
        return modelMapper.map(existingEmployee, EmployeeDTO.class);
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting employee by ID: {}", id);
        getExistingEmployee(id);
        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully");
    }

    @Override
    public void increaseSalaries(double amount, LocalDate currentDate) {
        log.info("Increasing salaries by amount: {}, currentDate: {}", amount, currentDate);
        if (currentDate.getDayOfMonth() == DAY_OF_SALARY_INCREASE) {
            employeeRepository.increaseSalaries(amount);
            log.info("Salaries increased successfully");
        } else {
            throw new SalaryIncreaseException("Salary increase not scheduled for today");
        }
    }

    @Override
    public boolean employeeExists(String email) {
        log.info("Checking if employee exists: email={}", email);
        return employeeRepository.existsByEmail(email);
    }


    @Override
    public EmployeeDTO getEmployeeDTOById(long id) {
        log.info("Getting employee DTO by ID: {}", id);
        Employee employee = getExistingEmployee(id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public Employee getExistingEmployee(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with the specified ID does not exist"));
    }

    @Override
    public void validateGymId(long gymId) {
        gymRepository.getById(gymId);
    }
}
