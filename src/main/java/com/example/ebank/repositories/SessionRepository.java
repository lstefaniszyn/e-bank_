package com.example.ebank.repositories;

import com.example.ebank.models.Session;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    
}
