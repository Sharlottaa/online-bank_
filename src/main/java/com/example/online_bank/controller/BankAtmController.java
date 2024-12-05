package com.example.online_bank.controller;

import com.example.online_bank.service.BankAtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.online_bank.dto.BankAtmDTO;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/atms")
public class BankAtmController {

    private final BankAtmService bankAtmService;

    @Autowired
    public BankAtmController(BankAtmService bankAtmService) {
        this.bankAtmService = bankAtmService;
    }

    @PostMapping
    public ResponseEntity<BankAtmDTO> createAtm(@RequestBody BankAtmDTO bankAtmDTO) {
        BankAtmDTO createdAtm = bankAtmService.createAtm(bankAtmDTO);
        return new ResponseEntity<>(createdAtm, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAtmDTO> getAtmById(@PathVariable Long id) {
        BankAtmDTO bankAtmDTO = bankAtmService.getAtmById(id);
        return ResponseEntity.ok(bankAtmDTO);
    }

    @GetMapping
    public ResponseEntity<List<BankAtmDTO>> getAllAtms() {
        List<BankAtmDTO> bankAtms = bankAtmService.getAllAtms();
        return ResponseEntity.ok(bankAtms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAtmDTO> updateAtm(@PathVariable Long id, @RequestBody BankAtmDTO bankAtmDTO) {
        BankAtmDTO updatedAtm = bankAtmService.updateAtm(id, bankAtmDTO);
        return ResponseEntity.ok(updatedAtm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtm(@PathVariable Long id) {
        bankAtmService.deleteAtm(id);
        return ResponseEntity.noContent().build();
    }
}
