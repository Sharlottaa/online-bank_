package com.example.online_bank.repository;

import com.example.online_bank.entity.BankOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankOfficeRepository extends JpaRepository<BankOffice, Long> {
}
