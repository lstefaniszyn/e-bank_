package com.example.ebank.mappers;

import com.example.ebank.generated.dto.PageDto;
import org.springframework.data.domain.Page;

public class PageMapper {
    
    public static PageDto toDto(Page page) {
        PageDto dto = new PageDto();
        dto.setSize(page.getSize());
        dto.setTotalElements(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setNumber(page.getNumber());
        return dto;
    }
}
