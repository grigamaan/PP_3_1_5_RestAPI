package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class RestApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public RestApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/api/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(userService.allUsers(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/api/users")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/api/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok((roleService.getAllRoles()).stream().toList());
    }

    @GetMapping("/admin")
    public ModelAndView userList(@AuthenticationPrincipal UserDetails userDetails, Model model,
                                 @ModelAttribute("newUser") User newUser) {
        ModelAndView admin = new ModelAndView("admin");
        User currentUser = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("newUser", newUser);
        return admin;
    }

    @GetMapping("/user")
    public ModelAndView userPage(Authentication authentication, Model model) {
        ModelAndView modelAndView = new ModelAndView("user");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return modelAndView;
    }

    @GetMapping("/login")
    ModelAndView login() {
        ModelAndView log = new ModelAndView("login");
        return log;
    }
}
