package com.ivanov.gym.repository;

import com.ivanov.gym.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail( String email);

}
