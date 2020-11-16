package com.example.ebank.repositories;

import com.example.ebank.models.Transaction;
import com.example.ebank.utils.ProfileManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TransactionRepository {

    private final JpaTransactionRepository jpaRepository;
    private final MockTransactionRepository mockRepository;
    private final ProfileManager profileManager;

    public TransactionRepository(JpaTransactionRepository jpaRepository,
        MockTransactionRepository mockRepository,
        ProfileManager profileManager) {
        this.jpaRepository = jpaRepository;
        this.mockRepository = mockRepository;
        this.profileManager = profileManager;
    }

    public Optional<Transaction> findById(Long id) {
        return profileManager.isMockProfileActive()
            ? mockRepository.findById(id)
            : jpaRepository.findById(id);
    }

    public Page<Transaction> findAll(Pageable pageable) {
        return profileManager.isMockProfileActive()
            ? mockRepository.findAll(pageable)
            : jpaRepository.findAll(pageable);
    }

    public Page<Transaction> findByValueDateBetween(Date startDate, Date endDate, Pageable pageable) {
        return profileManager.isMockProfileActive()
            ? mockRepository.findByValueDateBetween(startDate, endDate, pageable)
            : jpaRepository.findByDateBetween(startDate, endDate, pageable);
    }

    public Page<Transaction> findByValueDateBetweenAndAccountId(Date startDate, Date endDate, Long accountId, Pageable pageable) {
        return profileManager.isMockProfileActive()
            ? mockRepository.findByValueDateBetweenAndAccountId(startDate, endDate, accountId, pageable)
            : jpaRepository.findByDateBetweenAndAccountId(startDate, endDate, accountId, pageable);
    }

}
