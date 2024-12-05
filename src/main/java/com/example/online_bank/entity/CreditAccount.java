package com.example.online_bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "credit_accounts")
@Data
@Getter
@Setter
@NoArgsConstructor
public class CreditAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(nullable = false)
    private int months;

    @Column(name = "credit_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal creditAmount;

    @Column(name = "monthly_payment", precision = 15, scale = 2, nullable = false)
    private BigDecimal monthlyPayment;

    @Column(name = "interest_rate", precision = 4, scale = 2, nullable = false)
    private BigDecimal interestRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_account_id")
    private PaymentAccount paymentAccount;
}
