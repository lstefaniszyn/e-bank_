package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import com.example.ebank.utils.ProfileManager;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public List<Account> findAll() {
        return profileManager.isMockProfileActive()
                ? mockRepository.findAll()
                : jpaRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return profileManager.isMockProfileActive()
                ? mockRepository.findById(id)
                : jpaRepository.findById(id);
    }
    
    public List<Account> findByCustomerId(Long customerId){
    	return profileManager.isMockProfileActive()
                ? mockRepository.findByCustomerId(customerId)
                : jpaRepository.findByCustomerId(customerId);
    }

}
