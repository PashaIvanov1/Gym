package com.ivanov.gym.dto;

import com.ivanov.gym.model.Gym;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 20, message = "First Name should be between 2 and 20 characters")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 20, message = "Last Name should be between 2 and 20 characters")
    private String lastName;

    @Min(value = 18, message = "Age should be greater than 18")
    private int age;

    @NotEmpty(message = "Day of birth should not be empty")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format dd.mm.yyyy")
    private String dayOfBirth;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Wrong phone number example 380971234567 ")
    private String phone;

    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2, max = 20, message = "Position should be between 2 and 20 characters")
    private String position;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @Min(value = 1, message = "Experience should be greater than 1")
    private int experience;

    @Min(value = 100, message = "Salary should not be less than 100")
    private double salary;

    private Gym gymId;

}
