package com.zavlagas.spring.security.defence.security.services;

import com.zavlagas.spring.security.defence.entities.User;
import com.zavlagas.spring.security.defence.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BruteForceAttackService {

    @Autowired
    private LoginAttemptService loginAttemptService;
    
    public void validateLoginAttempt(User user) {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceedMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

}
