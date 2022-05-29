package com.equilator.controllers;

import com.equilator.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.equilator.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/main")
    @PreAuthorize("hasAuthority('access:admin')")
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers("email"));
        return "admin/main";
    }

    @GetMapping("/main/{sort}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String adminSort(Model model, @PathVariable("sort") String order) {
        model.addAttribute("users", userService.getAllUsers(order));
        return "admin/main";
    }

    @GetMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-info";
    }

    @PatchMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
         userService.updateUser(id, user);
        return "redirect:/admin/main";
    }

    @DeleteMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/main";
    }


}
