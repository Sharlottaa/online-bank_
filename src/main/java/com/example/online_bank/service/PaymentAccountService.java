package com.example.online_bank.service;


import com.example.online_bank.dto.PaymentAccountDTO;

import java.util.List;

public interface PaymentAccountService {
    PaymentAccountDTO createPaymentAccount(PaymentAccountDTO paymentAccountDTO);
    PaymentAccountDTO getPaymentAccountById(Long id);
    List<PaymentAccountDTO> getAllPaymentAccounts();
    PaymentAccountDTO updatePaymentAccount(Long id, PaymentAccountDTO paymentAccountDTO);
    void deletePaymentAccount(Long id);
}