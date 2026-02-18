package com.example.finance.service;

import com.example.finance.dto.MonthlyStatsResponse;
import com.example.finance.dto.TransactionRequest;
import com.example.finance.dto.TransactionResponse;
import com.example.finance.entity.*;
import com.example.finance.repository.CategoryRepository;
import com.example.finance.repository.TransactionRepository;
import com.example.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void createTransaction(TransactionRequest request) {

        User user = userRepository.findAll().get(0); // временно один пользователь

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .type(request.getType())
                .timestamp(LocalDateTime.now())
                .user(user)
                .category(category)
                .build();

        transactionRepository.save(transaction);
    }

    public List<TransactionResponse> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(t -> TransactionResponse.builder()
                        .id(t.getId())
                        .amount(t.getAmount())
                        .description(t.getDescription())
                        .type(t.getType())
                        .timestamp(t.getTimestamp())
                        .categoryName(t.getCategory().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<MonthlyStatsResponse> getMonthlyStats() {

        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDateTime.now();

        List<Transaction> transactions =
                transactionRepository.findByTimestampBetween(start, end);

        Map<String, List<Transaction>> grouped =
                transactions.stream()
                        .collect(Collectors.groupingBy(t -> t.getCategory().getName()));

        List<MonthlyStatsResponse> result = new ArrayList<>();

        for (String category : grouped.keySet()) {

            double income = grouped.get(category).stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double expense = grouped.get(category).stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            result.add(
                    MonthlyStatsResponse.builder()
                            .category(category)
                            .totalIncome(income)
                            .totalExpense(expense)
                            .build()
            );
        }

        return result;
    }
}
