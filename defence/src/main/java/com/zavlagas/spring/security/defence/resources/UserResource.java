package com.zavlagas.spring.security.defence.resources;

import com.zavlagas.spring.security.defence.entities.User;
import com.zavlagas.spring.security.defence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {


    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam String role,
                                           @RequestParam String isEnabled,
                                           @RequestParam String isNotLocked) {

        return ResponseEntity.ok(userService.createUser(
                        firstName,
                        lastName,
                        username,
                        password,
                        email,
                        role,
                        Boolean.parseBoolean(isEnabled),
                        Boolean.parseBoolean(isNotLocked)
                )
        );

    }
}
