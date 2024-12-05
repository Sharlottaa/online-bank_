package com.example.online_bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_offices")
@Data
public class BankOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String status;

    @Column(name = "can_place_atm")
    private boolean canPlaceAtm;

    @Column(name = "atm_count")
    private int atmCount;

    @Column(name = "can_issue_loans")
    private boolean canIssueLoans;

    @Column(name = "is_cash_withdrawal_available")
    private boolean isCashWithdrawalAvailable;

    @Column(name = "is_deposit_available")
    private boolean isDepositAvailable;

    @Column(name = "money_available")
    private BigDecimal moneyAvailable;

    @Column(name = "rental_cost")
    private BigDecimal rentalCost;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
}
