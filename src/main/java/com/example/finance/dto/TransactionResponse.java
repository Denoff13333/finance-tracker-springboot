package com.example.finance.dto;

import com.example.finance.entity.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private Long id;
    private Double amount;
    private String description;
    private TransactionType type;
    private LocalDateTime timestamp;
    private String categoryName;
}
