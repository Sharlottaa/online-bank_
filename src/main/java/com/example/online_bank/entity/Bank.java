package com.example.online_bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banks")
@Data
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "office_count", nullable = false)
    private int officeCount = 0;

    @Column(name = "atm_count", nullable = false)
    private int atmCount = 0;

    @Column(name = "employee_count", nullable = false)
    private int employeeCount = 0;

    @Column(name = "client_count", nullable = false)
    private int clientCount = 0;

    @Column(nullable = false)
    private int rating;

    @Column(name = "total_money", precision = 15, scale = 2)
    private BigDecimal totalMoney;

    @Column(name = "interest_rate", precision = 4, scale = 2)
    private BigDecimal interestRate;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankOffice> bankOffices;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAtm> bankAtms;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
}

