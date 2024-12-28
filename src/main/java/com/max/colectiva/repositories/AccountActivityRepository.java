package com.max.colectiva.repositories;

import com.max.colectiva.models.AccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountActivityRepository extends JpaRepository<AccountActivity, UUID> {



}