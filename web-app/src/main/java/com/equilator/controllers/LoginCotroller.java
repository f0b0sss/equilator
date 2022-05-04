package com.equilator.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginCotroller {

    @GetMapping("login.html")
    public String login(Model model) {

        return "login";
    }
}
