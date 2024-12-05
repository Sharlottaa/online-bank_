package com.example.online_bank.service;

import java.util.List;
import com.example.online_bank.dto.BankDTO;


public interface BankService {
    BankDTO createBank(BankDTO bankDTO);
    BankDTO getBankById(Long id);
    List<BankDTO> getAllBanks();
    BankDTO updateBank(Long id, BankDTO bankDTO);
    void deleteBank(Long id);
}