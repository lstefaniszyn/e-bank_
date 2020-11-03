package com.example.ebank.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "name", length = 250)
    private String familyName;
    
    @Column(name = "given_name", length = 250)
    private String givenName;
    
    @Column(name = "identity_key", length = 50)
    private String identityKey;
    
    public String getFamilyName() {
        return familyName;
    }
    
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String name) {
        this.givenName = name;
    }
    
    public String getIdentityKey() {
        return identityKey;
    }
    
    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }
    
}
