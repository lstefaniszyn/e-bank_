package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {

}
