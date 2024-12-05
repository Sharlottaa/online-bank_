package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BankOfficeDTO {
    private Long id;
    private String name;
    private String address;
    private String status;
    private boolean canPlaceAtm;
    private int atmCount;
    private boolean canIssueLoans;
    private boolean isCashWithdrawalAvailable;
    private boolean isDepositAvailable;
    private BigDecimal moneyAvailable;
    private BigDecimal rentalCost;
    private Long bankId;

    public BankOfficeDTO withBankId(Long bankId) {
        this.bankId = bankId;
        return this;
    }
}
