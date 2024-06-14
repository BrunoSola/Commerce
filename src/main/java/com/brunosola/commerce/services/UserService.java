package com.brunosola.commerce.services;

import com.brunosola.commerce.entities.Role;
import com.brunosola.commerce.entities.User;
import com.brunosola.commerce.projections.UserDetailsProjection;
import com.brunosola.commerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> userDetailsProjections = repository.searchUserAndRolesByEmail(username);
        if (userDetailsProjections.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(userDetailsProjections.get(0).getPassword());
        for (UserDetailsProjection projection : userDetailsProjections){
            user.addRoles(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }
}
