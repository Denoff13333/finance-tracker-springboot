package com.example.finance.dto;

import com.example.finance.entity.TransactionType;
import lombok.Data;

@Data
public class TransactionRequest {
    private Double amount;
    private String description;
    private TransactionType type;
    private Long categoryId;
}
