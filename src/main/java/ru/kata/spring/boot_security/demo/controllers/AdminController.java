package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.RoleService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id, Model roles) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id) {
        userService.saveUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("user") @Valid User user) {
        userService.addUser(user);
        return "admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.findUserById(userId));
        return "admin";
    }
}