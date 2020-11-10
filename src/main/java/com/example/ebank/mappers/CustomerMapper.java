package com.example.ebank.mappers;

import com.example.ebank.generated.dto.CustomerDto;
import com.example.ebank.generated.dto.CustomerListItemDto;
import com.example.ebank.models.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AccountMapper.class })
public abstract class CustomerMapper {

    public abstract CustomerDto toDto(Customer customer);

    public abstract CustomerListItemDto toListItemDto(Customer customer);

    public abstract List<CustomerListItemDto> toListDto(List<Customer> customers);
}
