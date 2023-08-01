package com.ivanov.gym.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 20, message = "First Name should be between 2 and 20 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 20, message = "Last Name should be between 2 and 20 characters")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 18, message = "Age should be greater than 18")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "Day of birth should not be empty")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format dd.mm.yyyy")
    @Column(name = "day_of_birth")
    private String dayOfBirth;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Wrong phone number example 380971234567 ")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2, max = 20, message = "Position should be between 2 and 20 characters")
    @Column(name = "position")
    private String position;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @Min(value = 1, message = "Experience should be greater than 1")
    @Column(name = "experience")
    private int experience;

    @Min(value = 100, message = "Salary should not be less than 100")
    @Column(name = "salary")
    private double salary;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

}
