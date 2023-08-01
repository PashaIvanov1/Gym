package com.ivanov.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/one")
    public String returnOne() {
        log.info("Returning program_one");
        return "program_one";
    }

    @GetMapping("/two")
    public String returnTwo() {
        log.info("Returning program_two");
        return "program_two";
    }

    @GetMapping("/three")
    public String returnThree() {
        log.info("Returning program_three");
        return "program_three";
    }
}
