package com.max.users.repositories;

import com.max.users.models.User;
import com.max.users.validators.main.users.UsernameValidatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UsernameValidatorRepository {

    Optional<User> findByUsername(String username);

}
