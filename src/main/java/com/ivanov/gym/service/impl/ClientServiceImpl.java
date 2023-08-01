package com.ivanov.gym.service.impl;

import com.ivanov.gym.dto.ClientDTO;
import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Gym;
import com.ivanov.gym.repository.GymRepository;
import com.ivanov.gym.service.GymService;
import com.ivanov.gym.model.Client;
import com.ivanov.gym.repository.ClientRepository;
import com.ivanov.gym.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final GymRepository gymRepository;
    private final ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public List<Client> getAll() {
        log.info("Getting all clients");
        return clientRepository.findAll();
    }

    @Override
    public ClientDTO getClientDTOById(long id) {
        log.info("Getting client DTO by ID: {}", id);
        Client client = getClientById(id);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public void save(ClientDTO clientDTO) {
        log.info("Saving client: {}", clientDTO);
        String email = clientDTO.getEmail();

        if (clientExists(email)) {
            log.info("Client with the specified information already exists: {}", clientDTO);
            throw new IllegalArgumentException("Client with the specified information already exists");
        }

        Client client = modelMapper.map(clientDTO, Client.class);
        Gym gym = gymRepository.findById(clientDTO.getGymId().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid gym ID"));
        client.setGym(gym);
        clientRepository.save(client);
        log.info("Client saved successfully");
    }

    @Override
    public Client getById(long id) {
        log.info("Getting client by ID: {}", id);
        return getClientById(id);
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting client by ID: {}", id);
        Client client = getClientById(id);
        clientRepository.delete(client);
        log.info("Client deleted successfully");
    }

    @Override
    public boolean clientExists(String email) {
        log.info("Checking if client exists: email={}", email);
        return clientRepository.existsByEmail(email);
    }

    @Override
    public void updateClientById(long id, ClientDTO clientDTO) {
        log.info("Updating client by ID: {}, clientDTO: {}", id, clientDTO);
        Client client = getClientById(id);

        modelMapper.map(clientDTO, client);

        if (clientDTO.getGymId() != null) {
            Optional<Gym> gym = gymRepository.findById(clientDTO.getGymId().getId());
            gym.ifPresent(client::setGym);
        }

        clientRepository.save(client);
        log.info("Client saved successfully");
    }

    @Override
    public Client getClientById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client with the specified ID does not exist"));
    }
}
