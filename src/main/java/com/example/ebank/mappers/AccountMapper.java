package com.example.ebank.mappers;

import com.example.ebank.generated.dto.AccountDto;
import com.example.ebank.models.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    public abstract AccountDto toDto(Account account);

    public abstract List<AccountDto> toListDto(List<Account> accounts);
}
