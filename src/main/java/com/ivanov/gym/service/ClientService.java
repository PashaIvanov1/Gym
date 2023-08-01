package com.ivanov.gym.service;

import com.ivanov.gym.dto.ClientDTO;
import com.ivanov.gym.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    void save(ClientDTO clientDTO);

    Client getById(long id);

    void deleteById(long id);

    boolean clientExists(String email);

    ClientDTO getClientDTOById(long id);

    void updateClientById(long id, ClientDTO clientDTO);

    Client getClientById(long id);
}
