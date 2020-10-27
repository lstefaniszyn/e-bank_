package com.example.ebank.repositories;

import com.example.ebank.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MockTransactionRepositoryTests {

    private final MockTransactionRepository transactionRepository = new MockTransactionRepository();

    @Test
    public void testFindAllTransactions() {
        List<Transaction> result = transactionRepository.findAll();

        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindTransactionById_expectPresent() {
        Optional<Transaction> result = transactionRepository.findById(1L);

        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindTransactionById_expectEmpty() {
        Optional<Transaction> result = transactionRepository.findById(925787655L);

        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByValueDateBetween() {
        Date startDate = createDate(2018, Calendar.MARCH, 1);
        Date endDate = createDate(2018, Calendar.MARCH, 31);
        int size = 5;
        Pageable pageable = PageRequest.of(0, size);
        Page<Transaction> result = transactionRepository.findByValueDateBetween(startDate, endDate, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).isNotEmpty().hasSize(size);
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
