package com.ivanov.gym.unitTest.service;

import com.ivanov.gym.dto.ClientDTO;
import com.ivanov.gym.model.Client;
import com.ivanov.gym.repository.ClientRepository;
import com.ivanov.gym.service.impl.ClientServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testGetAll() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAll();

        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(clients, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetClientDTOById_nonExistingId() {
        long clientId = 1L;

        Optional<Client> clientOptional = Optional.empty();
        Mockito.when(clientRepository.findById(clientId)).thenReturn(clientOptional);

        clientService.getClientDTOById(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSave_clientExists() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("John");
        clientDTO.setLastName("Doe");
        clientDTO.setEmail("john.doe@example.com");

        Mockito.when(clientRepository.existsByEmail(
                clientDTO.getEmail())).thenReturn(true);

        clientService.save(clientDTO);

        Mockito.verify(clientRepository, Mockito.times(1)).existsByEmail(
                clientDTO.getEmail());
        Mockito.verify(clientRepository, Mockito.times(0)).save(Mockito.any(Client.class));
    }

    @Test
    public void testGetById_existingId() {
        long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client result = clientService.getById(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
        Assert.assertEquals(client, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_nonExistingId() {
        long clientId = 1L;

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        clientService.getById(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteById_nonExistingId() {
        long clientId = 1L;

        clientService.deleteById(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).existsById(clientId);
        Mockito.verify(clientRepository, Mockito.times(0)).deleteById(clientId);
    }

    @Test
    public void testClientExists_existingClient() {
        String email = "john.doe@example.com";

        Mockito.when(clientRepository.existsByEmail(email)).thenReturn(true);

        boolean result = clientService.clientExists(email);

        Mockito.verify(clientRepository, Mockito.times(1)).existsByEmail(email);
        Assert.assertTrue(result);
    }

    @Test
    public void testClientExists_nonExistingClient() {
        String email = "john.doe@example.com";

        Mockito.when(clientRepository.existsByEmail(email)).thenReturn(false);

        boolean result = clientService.clientExists(email);

        Mockito.verify(clientRepository, Mockito.times(1)).existsByEmail(email);
        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateClientById_nonExistingId() {
        long clientId = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("John");
        clientDTO.setLastName("Doe");
        clientDTO.setEmail("john.doe@example.com");

        Optional<Client> clientOptional = Optional.empty();
        Mockito.when(clientRepository.findById(clientId)).thenReturn(clientOptional);

        clientService.updateClientById(clientId, clientDTO);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
        Mockito.verify(clientRepository, Mockito.times(0)).save(Mockito.any(Client.class));
    }
}
