package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void initializedDataBase() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);
        Set<Role> adminRoleSet = new HashSet<>();
        Set<Role> userRoleSet = new HashSet<>();
        System.out.println(adminRoleSet.add(roleAdmin));
        System.out.println(adminRoleSet.add(roleUser));
        System.out.println(userRoleSet.add(roleUser));
        User admin = new User("ad@gm.com", "Best", "Human", 99, "100");
        User admin2 = new User("ad2@gm.com", "God", "GodDontHaveLastName", 999, "101");
        User user1 = new User("Bill@mail.com", "Bill", "Door", 25, "200");
        User user2 = new User("Sam@mail.com", "Sam", "SoSerious", 66, "222");
        admin.setRoles(adminRoleSet);
        admin2.setRoles(adminRoleSet);
        user1.setRoles(userRoleSet);
        user2.setRoles(userRoleSet);
        userService.addUser(admin);
        userService.addUser(admin2);
        userService.addUser(user1);
        userService.addUser(user2);
    }
}
