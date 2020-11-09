package com.example.ebank.mappers;

import com.example.ebank.generated.dto.TransactionDto;
import com.example.ebank.generated.dto.TransactionPageDto;
import com.example.ebank.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Mappings({
        @Mapping(target = "value.amount", source = "transaction.amount"),
        @Mapping(target = "value.currency", source = "transaction.currency"),
        @Mapping(target = "date", source = "transaction.valueDate")
    })
    public abstract TransactionDto toDto(Transaction transaction);

    public abstract List<TransactionDto> toListDto(List<Transaction> transactions);

    public TransactionPageDto toTransactionPageDto(Page<Transaction> page) {
        TransactionPageDto pageDto = new TransactionPageDto();
        pageDto.setContent(toListDto(page.getContent()));
        pageDto.setPage(PageMapper.toDto(page));
        return pageDto;
    }
}
