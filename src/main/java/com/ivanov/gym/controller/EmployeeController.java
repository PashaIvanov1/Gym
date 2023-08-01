package com.ivanov.gym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ivanov.gym.dto.EmployeeDTO;
import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.exception.SalaryIncreaseException;
import com.ivanov.gym.service.EmployeeService;
import com.ivanov.gym.service.GymService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.ivanov.gym.enums.Constants.AMOUNT_OF_SALARY_INCREASE;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final GymService gymService;
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/all")
    public String showEmployeeAll(Model model) {
        log.info("Getting all employees");
        List<EmployeeDTO> employees = employeeService.getAll();
        model.addAttribute("employees", employees);
        return "showEmployee";
    }

    @ModelAttribute("employeeDTO")
    public EmployeeDTO getEmployeeDTO() {
        return new EmployeeDTO();
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        log.info("Showing add employee form");
        List<GymDTO> gyms = gymService.getAllGyms();
        model.addAttribute("gyms", gyms);
        return "addEmployee";
    }

    @PostMapping("/add")
    public String saveEmployee(@ModelAttribute("employeeDTO") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult, Model model) {
        log.info("Saving employee");

        if (bindingResult.hasErrors()) {
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            return "addEmployee";
        }

        try {
            employeeService.addEmployee(employeeDTO);
            log.info("Employee saved successfully");
            return "redirect:/employee/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Employee already exists");
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            return "addEmployee";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.info("Showing update form for employee with ID: {}", id);
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeDTOById(id);
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            model.addAttribute("employeeDTO", employeeDTO);
            return "update_employee";
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving employee: {}", e.getMessage());
            return "redirect:/employee/all";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, @ModelAttribute("employeeDTO") @Valid EmployeeDTO employeeDTO,
                                 BindingResult bindingResult, Model model) {
        log.info("Updating employee with ID: {}", id);
        if (bindingResult.hasErrors()) {
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "employeeDTO", bindingResult);
            return "update_employee";
        }

        try {
            employeeService.updateEmployee(id, employeeDTO);
            log.info("Employee updated successfully");
            return "redirect:/employee/all";
        } catch (IllegalArgumentException e) {
            log.error("Error updating employee: {}", e.getMessage());
            return "redirect:/employee/all";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id) {
        log.info("Deleting employee with ID: {}", id);
        try {
            employeeService.deleteById(id);
            log.info("Employee deleted successfully");
        } catch (IllegalArgumentException e) {
            log.error("Error deleting employee: {}", e.getMessage());
        }
        return "redirect:/employee/all";
    }

    @PutMapping("/increaseSalaries")
    public ResponseEntity<String> increaseSalaries() {
        log.info("Increasing salaries");
        try {
            employeeService.increaseSalaries(AMOUNT_OF_SALARY_INCREASE, LocalDate.now());
            return ResponseEntity.ok("Salaries increased successfully");
        } catch (SalaryIncreaseException e) {
            log.info("Salary increase not scheduled for today");
            return ResponseEntity.ok("Salary increase not scheduled for today");
        } catch (Exception e) {
            log.error("Error increasing salaries: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error increasing salaries: " + e.getMessage());
        }
    }
}
