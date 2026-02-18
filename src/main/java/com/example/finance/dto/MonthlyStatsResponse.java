package com.example.finance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthlyStatsResponse {

    private String category;
    private Double totalIncome;
    private Double totalExpense;
}
