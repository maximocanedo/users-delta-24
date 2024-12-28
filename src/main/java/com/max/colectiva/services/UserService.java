package com.max.colectiva.services;

import com.max.colectiva.models.User;
import com.max.colectiva.models.dto.req.UserSignupRequest;
import com.max.colectiva.models.exceptions.Fail;
import com.max.colectiva.validators.main.commons.StringValidator;
import com.max.colectiva.validators.main.users.PasswordValidator;
import com.max.colectiva.validators.main.users.UsernameValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    User getByUsername(String username) throws Fail;

    Slice<User> findAccounts(Pageable pageable, String q);

    User signup(UserSignupRequest request);


    /** Validadores **/
    UsernameValidator validateUsername(String username);

    PasswordValidator validatePassword(String password);

    StringValidator validateNames(String name, String fieldName);
}
