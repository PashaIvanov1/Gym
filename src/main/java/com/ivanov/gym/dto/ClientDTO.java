package com.ivanov.gym.dto;

import com.ivanov.gym.model.Gym;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 20, message = "First Name should be between 2 and 20 characters")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 20, message = "Last Name should be between 2 and 20 characters")
    private String lastName;

    @Min(value = 12, message = "Age should be greater than 12")
    @Max(value = 60, message = "Sorry, but you do not meet the criteria, contact the administrator")
    private int age;

    @NotEmpty(message = "Day of birth should not be empty")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format dd.mm.yyyy")
    private String dayOfBirth;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10,max = 12, message = "Wrong phone number (example 380971234567 ")
    private String phone;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    private Gym gymId;

}
