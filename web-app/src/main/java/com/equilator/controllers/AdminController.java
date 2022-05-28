package com.equilator.controllers;

import com.equilator.models.user.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.equilator.services.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    @PreAuthorize("hasAuthority('access:admin')")
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers("email"));
        //   model.addAttribute("users", userService.getUsers("USER"));
        return "main";
    }

    @GetMapping("/main/{sort}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String adminSort(Model model, @PathVariable("sort") String order) {
        model.addAttribute("users", userService.getAllUsers(order));
        return "main";
    }

    @GetMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user-info";
    }

    @PatchMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
         userService.updateUser(id, user);
        return "redirect:/main";
    }

    @DeleteMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/main";
    }


}
