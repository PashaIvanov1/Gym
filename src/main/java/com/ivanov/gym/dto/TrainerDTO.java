package com.ivanov.gym.dto;

import com.ivanov.gym.model.Gym;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class TrainerDTO {


    private Long id;
    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 20, message = "First Name should be between 2 and 20 characters")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 20, message = "Last Name should be between 2 and 20 characters")
    private String lastName;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 2, max = 1000, message = "Description should be between 2 and 1000 characters")
    private String description;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Wrong phone number example 380971234567 ")
    private String phoneNumber;

    @Min(value = 1, message = "Experience should be greater than 1")
    private int experienceYears;

    @Min(value = 18, message = "Age should be greater than 18")
    @Max(value = 60, message = "Sorry, but you do not meet the criteria, contact the administrator")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    private Gym gymId;

}
