package ar.com.colectiva.controllers;

import ar.com.colectiva.repositories.LoginAttemptRepository;
import ar.com.colectiva.services.AuthenticationService;
import ar.com.colectiva.models.dto.req.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        var token = authenticationService.login(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", token)
                .build();
    }

}
