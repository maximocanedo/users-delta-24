package com.max.users.services.impl;

import com.max.users.models.User;
import com.max.users.models.dto.req.UserSignupRequest;
import com.max.users.repositories.UserRepository;
import com.max.users.services.UserService;
import com.max.users.validators.main.users.PasswordValidator;
import com.max.users.validators.main.users.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    @Override
    public boolean existsByUsername(String username) {
        return repository.existsById(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User signup(UserSignupRequest request) {
        var username = validateUsername(request.getUsername()).build();
        var password = validatePassword(request.getPassword()).build();
    }


    @Override
    public UsernameValidator validateUsername(String username) {
        return new UsernameValidator(username)
                .useRepository(userRepository)
                .isAvailable()
                .meetsLimits()
                .meetsContentRequirements();
    }

    @Override
    public PasswordValidator validatePassword(String password) {
        return new PasswordValidator(password)
                .meetsLimits()
                .meetsContentRequirements();
    }


}

