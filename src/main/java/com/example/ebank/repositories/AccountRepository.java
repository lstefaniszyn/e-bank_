package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    List<Account> findByCustomerId(Long customerId);

    Optional<Account> findByIdAndCustomerId(Long id, Long customerId);

}
