package ar.com.colectiva.repositories;

import ar.com.colectiva.models.User;
import ar.com.colectiva.validators.main.users.UsernameValidatorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UsernameValidatorRepository {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:q% OR u.surname LIKE %:q% OR u.username LIKE %:q%")
    Slice<User> findUsers(@Param("q") String q, Pageable pageable);

}
