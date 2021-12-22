package com.zavlagas.spring.security.defence.security.services;

import com.zavlagas.spring.security.defence.entities.User;
import com.zavlagas.spring.security.defence.repositories.UserRepository;
import com.zavlagas.spring.security.defence.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BruteForceAttackService bruteForceAttackService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(username));
        UserPrincipal userPrincipal;
        if (user.isPresent()) {
            bruteForceAttackService.validateLoginAttempt(user.get());
            userPrincipal = new UserPrincipal(user.get());
            userRepository.save(user.get());
            return userPrincipal;
        }
        throw new UsernameNotFoundException("Username Not Found By Username : " + username);
    }
}
