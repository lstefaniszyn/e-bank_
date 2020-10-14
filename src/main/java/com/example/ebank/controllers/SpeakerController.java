package com.example.ebank.controllers;

import java.util.List;

import com.example.ebank.models.Speaker;
import com.example.ebank.repositories.SpeakerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping()
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Speaker  get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

}
