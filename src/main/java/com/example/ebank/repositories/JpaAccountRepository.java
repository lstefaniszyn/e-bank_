package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface JpaAccountRepository extends CrudRepository<Account, Long> {

}
