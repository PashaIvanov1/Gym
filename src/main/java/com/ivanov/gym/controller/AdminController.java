package com.ivanov.gym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ivanov.gym.model.Client;
import com.ivanov.gym.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final ClientRepository clientRepository;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/client/all")
    public String getClientAll(Model model) {
        log.info("Getting all clients");
        return showClientAll(model);
    }

    private String showClientAll(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "showClients";
    }
}
