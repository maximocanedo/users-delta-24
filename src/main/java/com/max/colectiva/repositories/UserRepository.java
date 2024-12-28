package com.max.colectiva.repositories;

import com.max.colectiva.models.User;
import com.max.colectiva.validators.main.users.UsernameValidatorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UsernameValidatorRepository {

    Optional<User> findByUsername(String username);

    Slice<User> findUsersByNameOrSurnameOrUsername(String q, Pageable pageable);

}
