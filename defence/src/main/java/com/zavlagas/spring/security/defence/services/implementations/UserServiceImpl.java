package com.zavlagas.spring.security.defence.services.implementations;

import com.zavlagas.spring.security.defence.constants.Role;
import com.zavlagas.spring.security.defence.entities.User;
import com.zavlagas.spring.security.defence.repositories.UserRepository;
import com.zavlagas.spring.security.defence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String firstName, String lastName, String username, String password, String email, String role, boolean isEnabled, boolean isNotLocked) {
        return userRepository.save(
                new User(
                        firstName
                        , lastName,
                        username,
                        password,
                        email,
                        checkAndGetRole(role),
                        isEnabled,
                        isNotLocked
                ));
    }

    private Role checkAndGetRole(String role) {
        Role userRole = Optional.ofNullable(Role.valueOf(role.toUpperCase())).orElse(Role.USER);
        return userRole;
    }
}
