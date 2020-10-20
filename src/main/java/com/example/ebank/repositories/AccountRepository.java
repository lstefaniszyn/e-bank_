package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import com.example.ebank.utils.ProfileManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountRepository {

    private final JpaAccountRepository jpaRepository;
    private final MockAccountRepository mockRepository;
    private final ProfileManager profileManager;

    public AccountRepository(JpaAccountRepository jpaRepository,
                             MockAccountRepository mockRepository,
                             ProfileManager profileManager) {
        this.jpaRepository = jpaRepository;
        this.mockRepository = mockRepository;
        this.profileManager = profileManager;
    }

    public Iterable<Account> findAll() {
        return profileManager.isMockProfileActive()
                ? mockRepository.findAll()
                : jpaRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return profileManager.isMockProfileActive()
                ? mockRepository.findById(id)
                : jpaRepository.findById(id);
    }
    
    public Page<Account> findByCustomerId(Long customerId, Pageable pageable){
    	return profileManager.isMockProfileActive()
                ? mockRepository.findByCustomerId(customerId, pageable)
                : jpaRepository.findByCustomerId(customerId, pageable);
    }

}
