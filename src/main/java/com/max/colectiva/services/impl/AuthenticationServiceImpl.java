package com.max.colectiva.services.impl;

import com.max.colectiva.models.exceptions.Fail;
import com.max.colectiva.services.AuthenticationService;
import com.max.colectiva.services.JwtService;
import com.max.colectiva.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthenticationServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder, UserService userService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) {
        var invalidCredentialsError = new Fail("Credenciales inválidas. ", HttpStatus.FORBIDDEN);
        var user = userService
                .findByUsername(username)
                .orElseThrow(() -> invalidCredentialsError);
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw invalidCredentialsError;
        // TODO Implementar TOTP y 2FA acá.
        return jwtService.generateToken(user);
    }

}
