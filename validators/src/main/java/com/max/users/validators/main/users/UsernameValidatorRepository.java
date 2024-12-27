package com.max.users.validators.main.users;

public interface UsernameValidatorRepository {
    boolean existsByUsername(String username);
}
