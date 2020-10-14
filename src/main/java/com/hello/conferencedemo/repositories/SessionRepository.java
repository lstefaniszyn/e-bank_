package com.hello.conferencedemo.repositories;

import com.hello.conferencedemo.models.Session;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    
}
