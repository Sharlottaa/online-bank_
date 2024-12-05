package com.example.online_bank.repository;

import com.example.online_bank.entity.BankAtm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BankAtmRepository extends JpaRepository<BankAtm, Long> {
}
