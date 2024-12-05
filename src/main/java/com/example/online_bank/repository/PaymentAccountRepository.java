package com.example.online_bank.repository;

import com.example.online_bank.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {
}
