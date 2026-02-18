package com.example.finance.controller;

import com.example.finance.dto.TransactionRequest;
import com.example.finance.dto.TransactionResponse;
import com.example.finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public List<TransactionResponse> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping("/transactions")
    public String createTransaction(@RequestBody TransactionRequest request) {
        transactionService.createTransaction(request);
        return "Transaction created";
    }

    @GetMapping("/stats/monthly")
    public List<?> getMonthlyStats() {
        return transactionService.getMonthlyStats();
    }
}
