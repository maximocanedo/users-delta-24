package com.max.users.services;

import com.max.users.models.User;
import com.max.users.models.dto.req.UserSignupRequest;
import com.max.users.validators.main.users.PasswordValidator;
import com.max.users.validators.main.users.UsernameValidator;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    User signup(UserSignupRequest request);


    /** Validadores **/
    UsernameValidator validateUsername(String username);

    PasswordValidator validatePassword(String password);
}
