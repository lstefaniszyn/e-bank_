package com.hello.conferencedemo.controllers;

import java.util.List;

import com.hello.conferencedemo.models.Session;
import com.hello.conferencedemo.repositories.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;
    
    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }
    
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // sets Http response to 201
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    
    
}
