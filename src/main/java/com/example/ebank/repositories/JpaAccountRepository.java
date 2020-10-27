package com.example.ebank.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.ebank.models.Account;

public interface JpaAccountRepository extends PagingAndSortingRepository<Account, Long> {
	
	Page<Account> findByCustomerId(Long id, Pageable pageable);

}
