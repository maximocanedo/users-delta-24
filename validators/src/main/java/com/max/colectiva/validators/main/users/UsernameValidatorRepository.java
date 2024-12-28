package com.max.colectiva.validators.main.users;

public interface UsernameValidatorRepository {
    boolean existsByUsername(String username);
}
