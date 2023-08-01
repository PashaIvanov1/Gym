package com.ivanov.gym.model;

import com.ivanov.gym.dto.GymDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gym")
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 1, max = 30, message = "City should be between 2 and 30 characters")
    @Column(name = "city")
    private String city;

    @NotEmpty(message = "Street should not be empty")
    @Size(min = 1, max = 50, message = "Street should be between 2 and 50 characters")
    @Column(name = "street")
    private String street;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Client> clients;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Trainer> trainers;

    public Gym(GymDTO gymDTO) {
        this.id = gymDTO.getId();
        this.city = gymDTO.getCity();
        this.street = gymDTO.getStreet();
    }
}
