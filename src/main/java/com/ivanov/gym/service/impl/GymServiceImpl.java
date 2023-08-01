package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Gym;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.GymService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GymServiceImpl implements GymService {

    private static final Logger log = LoggerFactory.getLogger(GymServiceImpl.class);

    private final GymRepository gymRepository;

    @Override
    public List<GymDTO> getAllGyms() {
        log.info("Getting all gyms");
        List<Gym> gyms = gymRepository.findAll();
        List<GymDTO> gymDTOs = gyms.stream()
                .map(GymDTO::new)
                .collect(Collectors.toList());
        log.info("Found {} gyms", gymDTOs.size());
        return gymDTOs;
    }

    @Override
    public void saveGym(GymDTO gymDTO) {
        log.info("Saving gym: {}", gymDTO);
        Gym gym = new Gym(gymDTO);
        gymRepository.save(gym);
        log.info("Gym saved successfully");
    }

    @Override
    public void updateGym(GymDTO gymDTO) {
        log.info("Updating gym: {}", gymDTO);
        Gym gym = getGymByIdInternal(gymDTO.getId());
        gym.setCity(gymDTO.getCity());
        gym.setStreet(gymDTO.getStreet());
        gymRepository.save(gym);
        log.info("Gym updated successfully");
    }

    @Override
    public GymDTO getGymById(long id) {
        log.info("Getting gym by ID: {}", id);
        Gym gym = getGymByIdInternal(id);
        GymDTO gymDTO = new GymDTO(gym);
        log.info("Found gym: {}", gymDTO);
        return gymDTO;
    }

    @Override
    public void deleteGymById(long id) {
        log.info("Deleting gym by ID: {}", id);
        Gym gym = getGymByIdInternal(id);
        gymRepository.delete(gym);
        log.info("Gym deleted successfully");
    }
    @Override
    public Gym getGymByIdInternal(long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gym with the specified ID does not exist"));
    }
}
