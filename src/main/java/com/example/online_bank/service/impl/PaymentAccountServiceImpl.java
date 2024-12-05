package com.example.online_bank.service.impl;

import com.example.online_bank.dto.PaymentAccountDTO;
import com.example.online_bank.entity.Bank;
import com.example.online_bank.entity.PaymentAccount;
import com.example.online_bank.entity.User;
import com.example.online_bank.mapper.PaymentAccountMapper;
import com.example.online_bank.repository.BankRepository;
import com.example.online_bank.repository.PaymentAccountRepository;
import com.example.online_bank.repository.UserRepository;
import com.example.online_bank.service.PaymentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;
    private final PaymentAccountMapper paymentAccountMapper;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    @Autowired
    public PaymentAccountServiceImpl(PaymentAccountRepository paymentAccountRepository,
                                     PaymentAccountMapper paymentAccountMapper,
                                     UserRepository userRepository,
                                     BankRepository bankRepository) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.paymentAccountMapper = paymentAccountMapper;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public PaymentAccountDTO createPaymentAccount(PaymentAccountDTO paymentAccountDTO) {
        // Проверка и установка User
        User user = userRepository.findById(paymentAccountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверка и установка Bank
        Bank bank = bankRepository.findById(paymentAccountDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        // Преобразование DTO в сущность
        PaymentAccount paymentAccount = paymentAccountMapper.toEntity(paymentAccountDTO);

        // Установка обязательных полей
        paymentAccount.setUser(user);
        paymentAccount.setBank(bank);

        // Установка значения balance по умолчанию, если оно не установлено
        if (paymentAccount.getBalance() == null) {
            paymentAccount.setBalance(BigDecimal.ZERO);
        }

        // Сохранение сущности
        PaymentAccount savedPaymentAccount = paymentAccountRepository.save(paymentAccount);
        return paymentAccountMapper.toDTO(savedPaymentAccount);
    }

    @Override
    public PaymentAccountDTO getPaymentAccountById(Long id) {
        PaymentAccount paymentAccount = paymentAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentAccount not found"));
        return paymentAccountMapper.toDTO(paymentAccount);
    }

    @Override
    public List<PaymentAccountDTO> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll()
                .stream()
                .map(paymentAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentAccountDTO updatePaymentAccount(Long id, PaymentAccountDTO paymentAccountDTO) {
        PaymentAccount existingPaymentAccount = paymentAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentAccount not found"));

        // Проверка и установка User
        User user = userRepository.findById(paymentAccountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверка и установка Bank
        Bank bank = bankRepository.findById(paymentAccountDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        // Обновление полей сущности
        paymentAccountMapper.updateEntityFromDTO(paymentAccountDTO, existingPaymentAccount);
        existingPaymentAccount.setUser(user);
        existingPaymentAccount.setBank(bank);

        // Установка значения balance по умолчанию, если оно не установлено
        if (existingPaymentAccount.getBalance() == null) {
            existingPaymentAccount.setBalance(BigDecimal.ZERO);
        }

        // Сохранение обновленной сущности
        PaymentAccount updatedPaymentAccount = paymentAccountRepository.save(existingPaymentAccount);
        return paymentAccountMapper.toDTO(updatedPaymentAccount);
    }

    @Override
    public void deletePaymentAccount(Long id) {
        PaymentAccount paymentAccount = paymentAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentAccount not found"));
        paymentAccountRepository.delete(paymentAccount);
    }
}