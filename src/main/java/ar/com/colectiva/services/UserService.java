package ar.com.colectiva.services;

import ar.com.colectiva.models.dto.req.UserSignupRequest;
import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.validators.main.commons.StringValidator;
import ar.com.colectiva.validators.main.users.PasswordValidator;
import ar.com.colectiva.validators.main.users.UsernameValidator;
import ar.com.colectiva.models.User;
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
