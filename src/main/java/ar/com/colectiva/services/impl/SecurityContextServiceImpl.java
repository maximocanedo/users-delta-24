package ar.com.colectiva.services.impl;

import ar.com.colectiva.models.User;
import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.repositories.UserRepository;
import ar.com.colectiva.services.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return userRepository.findByUsername(authentication.getName());
        }
        return Optional.empty();
    }

    @Override
    public User getCurrentUserOrDie() throws Fail {
        return getCurrentUser()
                .orElseThrow(() -> new Fail("No hay usuario autenticado. ", HttpStatus.UNAUTHORIZED));
    }

    @Override
    public User getCurrentActiveUserOrDie() throws Fail {
        var user = getCurrentUserOrDie();
        if(user.isEnabled()) return user;
        throw new Fail("Usuario inactivo. ", HttpStatus.UNAUTHORIZED);
    }
}
