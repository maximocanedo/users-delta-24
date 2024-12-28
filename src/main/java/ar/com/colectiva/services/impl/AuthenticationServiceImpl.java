package ar.com.colectiva.services.impl;

import ar.com.colectiva.models.LoginAttempt;
import ar.com.colectiva.models.User;
import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.repositories.LoginAttemptRepository;
import ar.com.colectiva.services.AuthenticationService;
import ar.com.colectiva.services.JwtService;
import ar.com.colectiva.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final LoginAttemptRepository loginAttemptRepository;

    @Autowired
    public AuthenticationServiceImpl(
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UserService userService,
            LoginAttemptRepository loginAttemptRepository
    ) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.loginAttemptRepository = loginAttemptRepository;
    }

    public LoginAttempt logAttempt(boolean result, String fingerprint, String approximateLocation, User user) {
        var attempt = new LoginAttempt();
        attempt.setFingerprint(fingerprint);
        attempt.setSuccessful(result);
        attempt.setUser(user);
        attempt.setApproximateLocation(approximateLocation);
        attempt.setAttemptedAt(new Timestamp(System.currentTimeMillis()));
        return loginAttemptRepository.save(attempt);
    }
    public LoginAttempt logAttempt(boolean result, String fingerprint, User user) {
        return logAttempt(result, fingerprint, "unknown", user);
    }
    public LoginAttempt logAttempt(boolean result, User user) {
        return logAttempt(result, "unknown", user);
    }
    public LoginAttempt logAttempt(String fingerprint, User user) {
        return logAttempt(true, fingerprint, user);
    }
    public LoginAttempt logAttempt(User user) {
        return logAttempt(true, user);
    }

    @Override
    public String login(String username, String password) {
        var invalidCredentialsError = new Fail("Credenciales inválidas. ", HttpStatus.FORBIDDEN);
        var user = userService
                .findByUsername(username)
                .orElseThrow(() -> invalidCredentialsError);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            logAttempt(false, user);
            throw invalidCredentialsError;
        }
        // TODO Implementar TOTP y 2FA acá.
        logAttempt(user);
        return jwtService.generateToken(user, false);
    }

}
