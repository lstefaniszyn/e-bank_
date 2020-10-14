package com.hello.conferencedemo.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Type;

@Entity(name = "speakers")
public class Speaker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speaker_id;
    
    private String first_name;
    private String last_name;
    private String title;
    private String company;
    private String speaker_bio;
    
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] speaker_photo;
    
    @ManyToMany(mappedBy = "speakers")
    private List<Session> sessions;
    
    public Speaker() {
        
    }
    
    public String getSpeaker_bio() {
        return speaker_bio;
    }
    
    public void setSpeaker_bio(String speaker_bio) {
        this.speaker_bio = speaker_bio;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getLast_name() {
        return last_name;
    }
    
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getFirst_name() {
        return first_name;
    }
    
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
