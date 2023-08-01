package com.ivanov.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String getLoginPage() {
        log.info("Accessing login page");
        return "login";
    }

    @GetMapping("/admin/home")
    public String adminPage() {
        log.info("Accessing admin page");
        return "body_admin";
    }

    @GetMapping("/user/home")
    public String userPage() {
        log.info("Accessing user page");
        return "body_user";
    }

    @GetMapping("/FAQs")
    public String getFAQs() {
        log.info("Accessing FAQs page");
        return "FAQs";
    }

    @GetMapping("/about")
    public String getAbout() {
        log.info("Accessing about page");
        return "about";
    }

}
