package com.example.online_bank.service;

import java.util.List;

import com.example.online_bank.dto.BankOfficeDTO;

public interface BankOfficeService {
    BankOfficeDTO createOffice(BankOfficeDTO bankOfficeDTO);
    BankOfficeDTO getOfficeById(Long id);
    List<BankOfficeDTO> getAllOffices();
    BankOfficeDTO updateOffice(Long id, BankOfficeDTO bankOfficeDTO);
    void deleteOffice(Long id);
}
