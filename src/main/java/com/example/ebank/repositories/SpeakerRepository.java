package com.example.ebank.repositories;

import com.example.ebank.models.Speaker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    
}
