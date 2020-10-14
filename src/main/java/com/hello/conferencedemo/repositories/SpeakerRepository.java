package com.hello.conferencedemo.repositories;

import com.hello.conferencedemo.models.Speaker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    
}
