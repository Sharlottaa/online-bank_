package com.example.online_bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "birth_date")
    private Date birthDate;

    private String position;

    @Column(name = "is_remote")
    private boolean isRemote;

    @Column(name = "can_issue_loans")
    private boolean canIssueLoans;

    @Column(name = "salary", precision = 15, scale = 2)
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "bank_office_id", nullable = false)
    private BankOffice bankOffice;
}
