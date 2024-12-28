package com.max.colectiva.services.impl;

import com.max.colectiva.models.AccountActivity;
import com.max.colectiva.models.AccountEvent;
import com.max.colectiva.models.User;
import com.max.colectiva.models.dto.req.UserSignupRequest;
import com.max.colectiva.models.exceptions.Fail;
import com.max.colectiva.repositories.AccountActivityRepository;
import com.max.colectiva.repositories.UserRepository;
import com.max.colectiva.services.UserService;
import com.max.colectiva.validators.main.commons.StringValidator;
import com.max.colectiva.validators.main.users.PasswordValidator;
import com.max.colectiva.validators.main.users.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final AccountActivityRepository accountActivityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository repository,
            AccountActivityRepository accountActivityRepository, PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.accountActivityRepository = accountActivityRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User getCurrentUserOrDie() throws Fail {
        return getCurrentUser()
                .orElseThrow(() -> new Fail("No hay usuario autenticado. ", HttpStatus.UNAUTHORIZED));
    }

    public User getCurrentActiveUserOrDie() throws Fail {
        var user = getCurrentUserOrDie();
        if(user.isEnabled()) return user;
        throw new Fail("Usuario inactivo. ", HttpStatus.UNAUTHORIZED);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return findByUsername(authentication.getName());
        }
        return Optional.empty();
    }

    public AccountActivity logActivity(AccountEvent event, String payload, User user) {
        var log = new AccountActivity();
        log.setUser(user);
        log.setEvent(event);
        log.setPayload(payload);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return accountActivityRepository.save(log);
    }
    public AccountActivity logActivity(AccountEvent event, String payload) {
        return logActivity(event, payload, getCurrentUserOrDie());
    }
    public AccountActivity logActivity(AccountEvent event) {
        return logActivity(event, "");
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
    public User getByUsername(String username) throws Fail {
        return findByUsername(username)
                .orElseThrow(() -> new Fail("Usuario no encontrado. ", HttpStatus.NOT_FOUND));
    }

    @Override
    public Slice<User> findAccounts(Pageable pageable, String q) {
        return repository.findUsersByNameOrSurnameOrUsername(q, pageable);
    }

    @Override
    public User signup(UserSignupRequest request) {
        var data = new User();
        data.setUsername(validateUsername(request.getUsername()).build());
        var cleanPassword = validatePassword(request.getPassword()).build();
        data.setName(validateNames(request.getName(), "name").build());
        data.setSurname(validateNames(request.getSurname(), "surname").build());
        data.setPassword(passwordEncoder.encode(cleanPassword));
        data.setEnabled(true);
        data.setExpired(false);
        data.setCredentialsExpired(false);
        data.setLocked(false);
        var user = repository.save(data);
        logActivity(AccountEvent.SIGNUP, "Creación de cuenta. ");
        return user;
    }


    @Override
    public UsernameValidator validateUsername(String username) {
        return new UsernameValidator(username)
                .useRepository(repository)
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

    @Override
    public StringValidator validateNames(String name, String fieldName) {
        return new StringValidator(name, fieldName)
            .isNotBlank("Este campo no debe estar vacío. ");
    }

}