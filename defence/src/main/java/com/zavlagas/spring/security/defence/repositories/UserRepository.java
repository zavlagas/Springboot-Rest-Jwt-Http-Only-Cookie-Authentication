package com.zavlagas.spring.security.defence.repositories;

import com.zavlagas.spring.security.defence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
