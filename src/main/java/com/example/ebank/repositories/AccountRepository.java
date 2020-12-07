package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    @Override
    List<Account> findAll();

    List<Account> findByCustomerId(Long id);

}
