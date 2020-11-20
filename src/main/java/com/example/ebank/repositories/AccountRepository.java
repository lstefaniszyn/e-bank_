package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    @Override
    List<Account> findAll();

    List<Account> findByCustomerId(Long id);

}
