package com.ivanov.gym.controller;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.dto.TrainerDTO;
import com.ivanov.gym.service.GymService;
import com.ivanov.gym.service.TrainerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final GymService gymService;

    private static final Logger log = LoggerFactory.getLogger(TrainerController.class);

    @ModelAttribute("trainerDTO")
    public TrainerDTO getTrainerDTO() {
        return new TrainerDTO();
    }

    @GetMapping("/add")
    public String showAddTrainerForm(Model model) {
        log.info("Showing add trainer form");
        List<GymDTO> gyms = gymService.getAllGyms();
        model.addAttribute("gyms", gyms);
        return "addTrainer";
    }

    @PostMapping("/add")
    public String addTrainer(@ModelAttribute("trainerDTO") @Valid TrainerDTO trainerDTO, BindingResult bindingResult, Model model) {
        log.info("Adding trainer: {}", trainerDTO);

        if (bindingResult.hasErrors()) {
            log.info("Invalid trainer details submitted");
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            return "addTrainer";
        }

        try {
            trainerService.addTrainer(trainerDTO);
            log.info("Trainer added successfully: {}", trainerDTO);
            return "redirect:/trainer/all";
        } catch (IllegalArgumentException e) {
            log.info("Trainer already exists: {}", trainerDTO);
            model.addAttribute("errorMessage", "Trainer already exists");
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            return "addTrainer";
        }
    }

    @GetMapping("/all")
    public String showAllTrainers(Model model) {
        log.info("Getting all trainers");
        List<TrainerDTO> trainers = trainerService.getAllTrainers();
        model.addAttribute("trainers", trainers);
        return "showTrainer";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.info("Showing update form for trainer with ID: {}", id);
        TrainerDTO trainerDTO = trainerService.getTrainerById(id);
        if (trainerDTO == null) {
            return "redirect:/trainer/all";
        }

        List<GymDTO> gyms = gymService.getAllGyms();
        model.addAttribute("gyms", gyms);
        model.addAttribute("trainerDTO", trainerDTO);
        return "update_trainer";
    }

    @PostMapping("/update/{id}")
    public String updateTrainer(@PathVariable("id") long id, @ModelAttribute("trainerDTO") @Valid TrainerDTO trainerDTO,
                                BindingResult bindingResult, Model model) {
        log.info("Updating trainer with ID: {}", id);
        if (bindingResult.hasErrors()) {
            log.info("Invalid trainer details submitted for update");
            List<GymDTO> gyms = gymService.getAllGyms();
            model.addAttribute("gyms", gyms);
            return "update_trainer";
        }

        trainerService.updateTrainer(id, trainerDTO);
        log.info("Trainer updated successfully: {}", trainerDTO);

        return "redirect:/trainer/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable("id") long id) {
        log.info("Deleting trainer with ID: {}", id);
        trainerService.deleteTrainer(id);
        log.info("Trainer deleted successfully with ID: {}", id);
        return "redirect:/trainer/all";
    }
}
