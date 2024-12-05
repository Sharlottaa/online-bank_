package com.example.online_bank.controller;

import com.example.online_bank.dto.CreditAccountDTO;
import com.example.online_bank.service.CreditAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-accounts")
public class CreditAccountController {

    private final CreditAccountService creditAccountService;

    @Autowired
    public CreditAccountController(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    /**
     * Создание нового кредитного счета
     */
    @PostMapping
    public ResponseEntity<CreditAccountDTO> createCreditAccount(@RequestBody CreditAccountDTO creditAccountDTO) {
        CreditAccountDTO createdCreditAccount = creditAccountService.createCreditAccount(creditAccountDTO);
        return new ResponseEntity<>(createdCreditAccount, HttpStatus.CREATED);
    }

    /**
     * Получение кредитного счета по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditAccountDTO> getCreditAccountById(@PathVariable Long id) {
        CreditAccountDTO creditAccountDTO = creditAccountService.getCreditAccountById(id);
        return ResponseEntity.ok(creditAccountDTO);
    }

    /**
     * Получение списка всех кредитных счетов
     */
    @GetMapping
    public ResponseEntity<List<CreditAccountDTO>> getAllCreditAccounts() {
        List<CreditAccountDTO> creditAccounts = creditAccountService.getAllCreditAccounts();
        return ResponseEntity.ok(creditAccounts);
    }

    /**
     * Обновление кредитного счета по ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<CreditAccountDTO> updateCreditAccount(@PathVariable Long id, @RequestBody CreditAccountDTO creditAccountDTO) {
        CreditAccountDTO updatedCreditAccount = creditAccountService.updateCreditAccount(id, creditAccountDTO);
        return ResponseEntity.ok(updatedCreditAccount);
    }

    /**
     * Удаление кредитного счета по ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditAccount(@PathVariable Long id) {
        creditAccountService.deleteCreditAccount(id);
        return ResponseEntity.noContent().build();
    }
}
