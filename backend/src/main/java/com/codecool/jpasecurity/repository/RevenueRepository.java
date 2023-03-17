package com.codecool.jpasecurity.repository;

import com.codecool.jpasecurity.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Optional<Revenue> findByOwner_Username(String username);
}
