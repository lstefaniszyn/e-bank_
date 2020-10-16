package com.example.ebank.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import com.example.ebank.models.Transaction;
import com.example.ebank.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern(DATE_FORMAT)).parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllByDatePaginated(
            @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size) {
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultsPage = transactionService.findInMonthPaginated(date, page, size);
        return resultsPage.getContent();
    }
}
