package com.ivanov.gym.service;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Gym;

import java.util.List;

public interface GymService {

    List<GymDTO> getAllGyms();

    void saveGym(GymDTO gymDTO);

    void updateGym(GymDTO gymDTO);

    GymDTO getGymById(long id);

    void deleteGymById(long id);

    Gym getGymByIdInternal(long id);
}
