package com.example.ebank.mappers;

import com.example.ebank.generated.dto.TransactionDto;
import com.example.ebank.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Mappings({
        @Mapping(target = "value.amount", source = "transaction.amount"),
        @Mapping(target = "value.currency", source = "transaction.currency")
    })
    public abstract TransactionDto toDto(Transaction transaction);

    public abstract List<TransactionDto> toListDto(List<Transaction> transactions);
}
