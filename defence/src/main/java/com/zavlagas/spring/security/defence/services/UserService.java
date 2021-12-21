package com.zavlagas.spring.security.defence.services;


import com.zavlagas.spring.security.defence.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getUsers();

    User createUser(String firstName, String lastName, String username, String password, String email, String role, boolean isEnabled, boolean isNotLocked);
}
