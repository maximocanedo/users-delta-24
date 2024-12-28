package ar.com.colectiva.validators.main.users;

public interface UsernameValidatorRepository {
    boolean existsByUsername(String username);
}
