package com.example.online_bank.repository;

import com.example.online_bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}