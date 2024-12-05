package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentAccountDTO {
    private Long id;
    private BigDecimal balance;
    private Long userId;
    private Long bankId;
}
