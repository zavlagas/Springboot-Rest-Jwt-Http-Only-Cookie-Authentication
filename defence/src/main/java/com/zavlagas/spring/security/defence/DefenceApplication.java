package com.zavlagas.spring.security.defence;

import com.zavlagas.spring.security.defence.constants.Role;
import com.zavlagas.spring.security.defence.entities.User;
import com.zavlagas.spring.security.defence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class DefenceApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DefenceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.saveAll(
                List.of(
                        new User(
                                "Nick",
                                "Zavlagas",
                                "zavlagas",
                                bCryptPasswordEncoder.encode("12345"),
                                "zavlagas@hotmail.com",
                                Role.USER,
                                true,
                                true
                        )
                )
        );
    }
}
