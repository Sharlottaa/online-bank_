package com.example.online_bank.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_atms")
@Data
@Getter
@Setter
@NoArgsConstructor
public class BankAtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 50)
    private String status;

    @Column(name = "is_cash_withdrawal_available", nullable = false)
    private boolean isCashWithdrawalAvailable = true;

    @Column(name = "is_deposit_available", nullable = false)
    private boolean isDepositAvailable = true;

    @Column(name = "money_available", precision = 15, scale = 2)
    private BigDecimal moneyAvailable;

    @Column(name = "maintenance_cost", precision = 15, scale = 2)
    private BigDecimal maintenanceCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private BankOffice office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
