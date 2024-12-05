package com.example.online_bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "birth_date")
    private java.util.Date birthDate;

    @Column(name = "place_of_work", length = 100)
    private String placeOfWork;

    @Column(name = "monthly_income", precision = 15, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(name = "credit_rating", nullable = false)
    private int creditRating;

    // Связь с банками, которыми пользуется пользователь
    @ManyToMany
    @JoinTable(
            name = "user_banks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private List<Bank> banks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditAccount> creditAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentAccount> paymentAccounts = new ArrayList<>();
}