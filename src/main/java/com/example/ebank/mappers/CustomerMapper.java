package com.example.ebank.mappers;

import com.example.ebank.models.Customer;
import org.mapstruct.Mapper;
import org.openapitools.dto.CustomerDto;
import org.openapitools.dto.CustomerListItemDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

	public abstract CustomerDto toDto(Customer customer);

    public abstract CustomerListItemDto toListItemDto(Customer customer);
    
	public abstract List<CustomerListItemDto> toListDto(List<Customer> customers);
}
