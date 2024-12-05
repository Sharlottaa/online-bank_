package com.example.online_bank.controller;

import com.example.online_bank.service.BankOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.online_bank.dto.BankOfficeDTO;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/offices")
public class BankOfficeController {

    private final BankOfficeService bankOfficeService;

    @Autowired
    public BankOfficeController(BankOfficeService bankOfficeService) {
        this.bankOfficeService = bankOfficeService;
    }

    @PostMapping
    public ResponseEntity<BankOfficeDTO> createOffice(@RequestBody BankOfficeDTO bankOfficeDTO) {
        BankOfficeDTO createdOffice = bankOfficeService.createOffice(bankOfficeDTO);
        return new ResponseEntity<>(createdOffice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankOfficeDTO> getOfficeById(@PathVariable Long id) {
        BankOfficeDTO bankOfficeDTO = bankOfficeService.getOfficeById(id);
        return ResponseEntity.ok(bankOfficeDTO);
    }

    @GetMapping
    public ResponseEntity<List<BankOfficeDTO>> getAllOffices() {
        List<BankOfficeDTO> offices = bankOfficeService.getAllOffices();
        return ResponseEntity.ok(offices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankOfficeDTO> updateOffice(@PathVariable Long id, @RequestBody BankOfficeDTO bankOfficeDTO) {
        BankOfficeDTO updatedOffice = bankOfficeService.updateOffice(id, bankOfficeDTO);
        return ResponseEntity.ok(updatedOffice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        bankOfficeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }
}
