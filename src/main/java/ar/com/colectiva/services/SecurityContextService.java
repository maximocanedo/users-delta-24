package ar.com.colectiva.services;

import ar.com.colectiva.models.User;
import ar.com.colectiva.models.exceptions.Fail;

import java.util.Optional;

public interface SecurityContextService {

    Optional<User> getCurrentUser();

    User getCurrentUserOrDie() throws Fail;

    User getCurrentActiveUserOrDie() throws Fail;

}
