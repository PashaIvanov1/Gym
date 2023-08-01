package com.ivanov.gym.repository;

import com.ivanov.gym.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
