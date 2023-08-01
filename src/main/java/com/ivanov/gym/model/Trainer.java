package com.ivanov.gym.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainer")
public class Trainer {

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

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 2, max = 1000, message = "Description should be between 2 and 1000 characters")
    @Column(name = "description")
    private String description;


    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Wrong phone number example 380971234567 ")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Min(value = 1, message = "Experience should be greater than 1")
    @Column(name = "experience_years")
    private int experienceYears;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @Min(value = 18, message = "Age should be greater than 18")
    @Max(value = 60, message = "Sorry, but you do not meet the criteria, contact the administrator")
    @Column(name = "age")
    private int age;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

}
