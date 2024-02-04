package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findUserById((long) id));
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "/new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser((long)id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.findUserById(userId));
        return "admin";
    }

//    @GetMapping("/user/{id}")
//    public String userPage(Model model, @PathVariable("id") int id) {
//        User user = userService.findUserById(((long) id));
//        model.addAttribute("user", userService.findUserById((long) id));
//        return "user";
//    }

//    @GetMapping("/user")
//    public String userPage(Authentication authentication, Model model) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userService.findUserById(Long.valueOf(userDetails.getUsername()));
//        model.addAttribute("user", user);
//        return "user";
//    }

    @GetMapping("/user")
    public String userPage(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user";
    }
}