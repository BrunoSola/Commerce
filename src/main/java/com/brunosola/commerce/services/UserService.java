package com.brunosola.commerce.services;

import com.brunosola.commerce.dto.UserDTO;
import com.brunosola.commerce.dto.UserInsertDTO;
import com.brunosola.commerce.entities.Role;
import com.brunosola.commerce.entities.User;
import com.brunosola.commerce.projections.UserDetailsProjection;
import com.brunosola.commerce.repositories.RoleRepository;
import com.brunosola.commerce.repositories.UserRepository;
import com.brunosola.commerce.services.exceptions.DatabaseException;
import com.brunosola.commerce.services.exceptions.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> userDetailsProjections = repository.searchUserAndRolesByEmail(username);
        if (userDetailsProjections.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setEmail(userDetailsProjections.get(0).getUsername());
        user.setPassword(userDetailsProjections.get(0).getPassword());
        for (UserDetailsProjection projection : userDetailsProjections){
            user.addRoles(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    protected User authenticated(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return repository.findByEmail(username).get();
        }catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }

    public List<UserDTO> findAll() {
        List<User> users = repository.searchAll();
        return users.stream().map(UserDTO::new).toList();
    }

    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    private void copyDtoToEntity(UserInsertDTO dto, User entity){
        if (!repository.existsByEmail(dto.getEmail())) {
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPhone(dto.getPhone());
            entity.setBirthDate(dto.getBirthDate());
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity.getRoles().clear();
            for (String authority : dto.getRoles()) {
                if (authority.equalsIgnoreCase("ROLE_ADMIN")) throw new ResourceNotFoundException("Authority (ROLE_ADMIN) só pode ser cadastrado por usuário autorizado.");
                if (roleRepository.findByAuthority(authority) != null) {
                    entity.getRoles().add(roleRepository.findByAuthority(authority));
                } else {
                    throw new ResourceNotFoundException("Authority invalido.");
                }
            }
        }else {
            throw new DatabaseException("E-mail ja cadastrado: " + dto.getEmail());
        }
    }
}
