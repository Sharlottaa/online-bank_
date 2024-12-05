package com.example.online_bank.controller;

import com.example.online_bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.online_bank.dto.BankDTO;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<BankDTO> createBank(@RequestBody BankDTO bankDTO) {
        BankDTO createdBank = bankService.createBank(bankDTO);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> getBankById(@PathVariable Long id) {
        BankDTO bankDTO = bankService.getBankById(id);
        return ResponseEntity.ok(bankDTO);
    }

    @GetMapping
    public ResponseEntity<List<BankDTO>> getAllBanks() {
        List<BankDTO> bankDTOs = bankService.getAllBanks();
        return ResponseEntity.ok(bankDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDTO> updateBank(@PathVariable Long id, @RequestBody BankDTO bankDTO) {
        BankDTO updatedBank = bankService.updateBank(id, bankDTO);
        return ResponseEntity.ok(updatedBank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}
