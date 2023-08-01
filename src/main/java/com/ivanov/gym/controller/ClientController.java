package com.ivanov.gym.controller;

import com.ivanov.gym.dto.ClientDTO;
import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.model.Client;
import com.ivanov.gym.service.ClientService;
import com.ivanov.gym.service.GymService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final GymService gymService;

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);


    @GetMapping("/all")
    public String showClientAll(Model model) {
        log.info("Getting all clients");
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);

        return "showClients";
    }

    @ModelAttribute("clientDTO")
    public ClientDTO getClientDTO() {
        return new ClientDTO();
    }

    @GetMapping("/add")
    public String showAddClientForm(Model model, @ModelAttribute("clientDTO") ClientDTO clientDTO, BindingResult bindingResult) {
        log.info("Showing add client form");

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.clientDTO", bindingResult);
        }

        if (!model.containsAttribute("clientDTO")) {
            model.addAttribute("clientDTO", new ClientDTO());
        }

        List<GymDTO> gyms = gymService.getAllGyms();

        model.addAttribute("gyms", gyms);

        return "addClient";
    }

    @PostMapping("/add")
    public String saveClient(@ModelAttribute("clientDTO") @Valid ClientDTO clientDTO, BindingResult bindingResult, Model model) {
        log.info("Saving client: {}", clientDTO);

        if (bindingResult.hasErrors()) {
            return showAddClientForm(model, clientDTO, bindingResult);
        }

        try {
            clientService.save(clientDTO);
            log.info("Client saved successfully");
            return "redirect:/client/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Client already exists");
            return showAddClientForm(model, clientDTO, bindingResult);
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.info("Showing update form for client with ID: {}", id);

        try {
            ClientDTO clientDTO = clientService.getClientDTOById(id);
            List<GymDTO> gyms = gymService.getAllGyms();

            model.addAttribute("gyms", gyms);
            model.addAttribute("clientDTO", clientDTO);

            return "update_client";
        } catch (IllegalArgumentException e) {
            log.error("Error getting client: {}", e.getMessage());
            return "redirect:/update_client";
        }
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") long id, @ModelAttribute("clientDTO") @Valid ClientDTO clientDTO,
                               BindingResult bindingResult, Model model) {
        log.info("Updating client with ID: {}", id);
        if (bindingResult.hasErrors()) {
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "clientDTO", bindingResult);
            return "update_client";
        }

        try {
            clientService.updateClientById(id, clientDTO);
            log.info("Client updated successfully");
            return "redirect:/client/all";
        } catch (IllegalArgumentException e) {
            log.error("Error updating client: {}", e.getMessage());
            return "redirect:/update_client";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable(value = "id") long id) {
        log.info("Deleting client with ID: {}", id);
        clientService.deleteById(id);
        log.info("Client deleted successfully");
        return "redirect:/client/all";
    }
}
