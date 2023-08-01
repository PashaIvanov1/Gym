package com.ivanov.gym.controller;

import com.ivanov.gym.dto.GymDTO;
import com.ivanov.gym.service.GymService;
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
@RequestMapping("/gym")
@AllArgsConstructor
public class GymController {

    private final GymService gymService;

    private static final Logger log = LoggerFactory.getLogger(GymController.class);


    @GetMapping("/all")
    public String showGymAll(Model model) {
        log.info("Getting all gyms");
        List<GymDTO> gyms = gymService.getAllGyms();
        model.addAttribute("gyms", gyms);
        return "showGym";
    }

    @ModelAttribute("gymDTO")
    public GymDTO getGymDTO() {
        return new GymDTO();
    }

    @GetMapping("/add")
    public String showAddGymForm(Model model) {
        log.info("Showing add gym form");
        return "addGym";
    }

    @PostMapping("/add")
    public String saveGym(@ModelAttribute("gymDTO") @Valid GymDTO gymDTO, BindingResult bindingResult, Model model) {
        log.info("Saving gym: {}", gymDTO);
        if (bindingResult.hasErrors()) {
            return showAddGymForm(model);
        }
        gymService.saveGym(gymDTO);
        log.info("Gym saved successfully");
        return "redirect:/gym/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.info("Showing update form for gym with ID: {}", id);
        GymDTO gymDTO = gymService.getGymById(id);
        if (gymDTO == null) {
            return "redirect:/gym/all";
        }

        model.addAttribute("gymDTO", gymDTO);
        return "update_gym";
    }

    @PostMapping("/update/{id}")
    public String updateGym(@PathVariable("id") long id, @ModelAttribute("gymDTO") @Valid GymDTO gymDTO,
                            BindingResult bindingResult) {
        log.info("Updating gym with ID: {}", id);
        if (bindingResult.hasErrors()) {
            return "update_gym";
        }

        gymService.updateGym(gymDTO);

        log.info("Gym update successfully");

        return "redirect:/gym/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteGym(@PathVariable(value = "id") long id) {
        log.info("Deleting gym with ID: {}", id);
        gymService.deleteGymById(id);
        log.info("Gym deleted successfully");
        return "redirect:/gym/all";
    }
}
