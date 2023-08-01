package com.ivanov.gym.repository;

import com.ivanov.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByEmail(String email);
}
