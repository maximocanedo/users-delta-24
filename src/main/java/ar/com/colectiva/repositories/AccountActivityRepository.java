package ar.com.colectiva.repositories;

import ar.com.colectiva.models.AccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountActivityRepository extends JpaRepository<AccountActivity, UUID> {



}