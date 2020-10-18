package com.example.ebank.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import com.example.ebank.models.Transaction;
import com.example.ebank.services.TransactionService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern(DATE_FORMAT)).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Get account's transactions", nickname = "getAllByDatePaginated", notes = "", response = Transaction.class, responseContainer = "List", tags = {
            "account", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Transaction.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid client id supplied"),
            @ApiResponse(code = 404, message = "Client not found") })
    @RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
    public List<Transaction> getAllByDatePaginated(
            @ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
            @ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
            @ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultsPage = transactionService.findInMonthPaginated(date, page, size);
        return resultsPage.getContent();
    }
}
