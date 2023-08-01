package com.ivanov.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/success")
public class SuccessController {

    private static final Logger log = LoggerFactory.getLogger(SuccessController.class);

    @GetMapping
    public String getSuccess() {
        log.info("Returning success");
        return "success";
    }
}
