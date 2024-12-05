package com.example.online_bank.controller;
import com.example.online_bank.dto.PaymentAccountDTO;
import com.example.online_bank.service.PaymentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-accounts")
public class PaymentAccountController {

    private final PaymentAccountService paymentAccountService;

    @Autowired
    public PaymentAccountController(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    /**
     * Создание нового платежного счета
     */
    @PostMapping
    public ResponseEntity<PaymentAccountDTO> createPaymentAccount(@RequestBody PaymentAccountDTO paymentAccountDTO) {
        PaymentAccountDTO createdPaymentAccount = paymentAccountService.createPaymentAccount(paymentAccountDTO);
        return new ResponseEntity<>(createdPaymentAccount, HttpStatus.CREATED);
    }

    /**
     * Получение платежного счета по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentAccountDTO> getPaymentAccountById(@PathVariable Long id) {
        PaymentAccountDTO paymentAccountDTO = paymentAccountService.getPaymentAccountById(id);
        return ResponseEntity.ok(paymentAccountDTO);
    }

    /**
     * Получение списка всех платежных счетов
     */
    @GetMapping
    public ResponseEntity<List<PaymentAccountDTO>> getAllPaymentAccounts() {
        List<PaymentAccountDTO> paymentAccounts = paymentAccountService.getAllPaymentAccounts();
        return ResponseEntity.ok(paymentAccounts);
    }

    /**
     * Обновление платежного счета по ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentAccountDTO> updatePaymentAccount(@PathVariable Long id, @RequestBody PaymentAccountDTO paymentAccountDTO) {
        PaymentAccountDTO updatedPaymentAccount = paymentAccountService.updatePaymentAccount(id, paymentAccountDTO);
        return ResponseEntity.ok(updatedPaymentAccount);
    }

    /**
     * Удаление платежного счета по ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentAccount(@PathVariable Long id) {
        paymentAccountService.deletePaymentAccount(id);
        return ResponseEntity.noContent().build();
    }
}