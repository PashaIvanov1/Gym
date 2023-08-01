package com.ivanov.gym.model;

import com.ivanov.gym.dto.ClientDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

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

    @Min(value = 12, message = "Age should be greater than 12")
    @Max(value = 60, message = "Sorry, but you do not meet the criteria, contact the administrator")
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

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public Client(ClientDTO clientDTO) {
        this.id = clientDTO.getId();
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.age = clientDTO.getAge();
        this.dayOfBirth = clientDTO.getDayOfBirth();
        this.phone = clientDTO.getPhone();
        this.email = clientDTO.getEmail();
        this.gym = clientDTO.getGymId();
    }

    public Client(String firstName, String lastName, int age, String dayOfBirth, String phone, String email, Gym gym) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dayOfBirth = dayOfBirth;
        this.phone = phone;
        this.email = email;
        this.gym = gym;

    }
}
