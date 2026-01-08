package org.example.sbgroup2.dto;

import lombok.Data;
import org.example.sbgroup2.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class AccounceFormDTO {
    private LocalDate date;
    private String name;
    private String expenseHead;
    private BigDecimal expenseAmount;
}
