package com.example.online_bank.service;

import java.util.List;

import com.example.online_bank.dto.BankAtmDTO;


public interface BankAtmService {
    BankAtmDTO createAtm(BankAtmDTO bankAtmDTO);
    BankAtmDTO getAtmById(Long id);
    List<BankAtmDTO> getAllAtms();
    BankAtmDTO updateAtm(Long id, BankAtmDTO bankAtmDTO);
    void deleteAtm(Long id);
}