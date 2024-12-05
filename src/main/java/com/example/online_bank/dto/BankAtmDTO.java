package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BankAtmDTO {
    private Long id;
    private String name;
    private String address;
    private String status;
    private boolean isCashWithdrawalAvailable;
    private boolean isDepositAvailable;
    private BigDecimal moneyAvailable;
    private BigDecimal maintenanceCost;
    private Long bankId;
    private Long officeId;
    private Long employeeId;
}
