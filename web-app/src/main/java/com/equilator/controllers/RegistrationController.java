package com.equilator.controllers;

import org.springframework.stereotype.Controller;
import services.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }







}
