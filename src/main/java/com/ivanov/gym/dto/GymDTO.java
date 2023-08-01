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
public class GymDTO {

    private Long id;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 1, max = 30, message = "City should be between 2 and 30 characters")
    private String city;

    @NotEmpty(message = "Street should not be empty")
    @Size(min = 1, max = 50, message = "Street should be between 2 and 50 characters")
    private String street;

    public GymDTO(Gym gym) {
        this.id = gym.getId();
        this.city = gym.getCity();
        this.street = gym.getStreet();
    }
}
