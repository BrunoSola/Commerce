package com.brunosola.commerce.services;

import com.brunosola.commerce.entities.User;
import com.brunosola.commerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(Long userId){
        User user = userService.authenticated();
        if (!user.hasRole("ROLE_ADMIN") && !user.getId().equals(userId)){
            throw new ForbiddenException("Acesso negado.");
        }
    }
}
