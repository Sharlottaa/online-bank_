package com.example.online_bank.service;

import com.example.online_bank.dto.CreditAccountDTO;

import java.util.List;

public interface CreditAccountService {
    CreditAccountDTO createCreditAccount(CreditAccountDTO creditAccountDTO);

    CreditAccountDTO getCreditAccountById(Long id);

    List<CreditAccountDTO> getAllCreditAccounts();

    CreditAccountDTO updateCreditAccount(Long id, CreditAccountDTO creditAccountDTO);

    void deleteCreditAccount(Long id);
}
