package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditAccountDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private int months;
    private BigDecimal creditAmount;
    private BigDecimal monthlyPayment;
    private BigDecimal interestRate;
    private Long userId;
    private Long bankId;
    private Long employeeId;
    private Long paymentAccountId;
}
