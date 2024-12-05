package com.example.online_bank.service.impl;

import com.example.online_bank.entity.CreditAccount;
import com.example.online_bank.repository.CreditAccountRepository;
import com.example.online_bank.service.CreditAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.online_bank.dto.CreditAccountDTO;
import com.example.online_bank.entity.*;
import com.example.online_bank.mapper.CreditAccountMapper;
import com.example.online_bank.repository.*;

import java.util.stream.Collectors;

@Service
public class CreditAccountServiceImpl implements CreditAccountService {

    private final CreditAccountRepository creditAccountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final CreditAccountMapper creditAccountMapper;

    @Autowired
    public CreditAccountServiceImpl(CreditAccountRepository creditAccountRepository,
                                    UserRepository userRepository,
                                    BankRepository bankRepository,
                                    EmployeeRepository employeeRepository,
                                    PaymentAccountRepository paymentAccountRepository,
                                    CreditAccountMapper creditAccountMapper) {
        this.creditAccountRepository = creditAccountRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.employeeRepository = employeeRepository;
        this.paymentAccountRepository = paymentAccountRepository;
        this.creditAccountMapper = creditAccountMapper;
    }

    @Override
    public CreditAccountDTO createCreditAccount(CreditAccountDTO creditAccountDTO) {
        User user = userRepository.findById(creditAccountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bank bank = bankRepository.findById(creditAccountDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        Employee employee = employeeRepository.findById(creditAccountDTO.getEmployeeId())
                .orElse(null);

        PaymentAccount paymentAccount = paymentAccountRepository.findById(creditAccountDTO.getPaymentAccountId())
                .orElse(null);

        CreditAccount creditAccount = creditAccountMapper.toEntity(creditAccountDTO);
        creditAccount.setUser(user);
        creditAccount.setBank(bank);
        creditAccount.setEmployee(employee);
        creditAccount.setPaymentAccount(paymentAccount);

        creditAccount = creditAccountRepository.save(creditAccount);

        return creditAccountMapper.toDTO(creditAccount);
    }

    @Override
    public CreditAccountDTO getCreditAccountById(Long id) {
        CreditAccount creditAccount = creditAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditAccount not found"));
        return creditAccountMapper.toDTO(creditAccount);
    }

    @Override
    public List<CreditAccountDTO> getAllCreditAccounts() {
        List<CreditAccount> creditAccounts = creditAccountRepository.findAll();
        return creditAccounts.stream().map(creditAccountMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CreditAccountDTO updateCreditAccount(Long id, CreditAccountDTO creditAccountDTO) {
        CreditAccount creditAccount = creditAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditAccount not found"));

        creditAccountMapper.updateEntityFromDTO(creditAccountDTO, creditAccount);

        User user = userRepository.findById(creditAccountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bank bank = bankRepository.findById(creditAccountDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        Employee employee = employeeRepository.findById(creditAccountDTO.getEmployeeId())
                .orElse(null);

        PaymentAccount paymentAccount = paymentAccountRepository.findById(creditAccountDTO.getPaymentAccountId())
                .orElse(null);

        creditAccount.setUser(user);
        creditAccount.setBank(bank);
        creditAccount.setEmployee(employee);
        creditAccount.setPaymentAccount(paymentAccount);

        creditAccount = creditAccountRepository.save(creditAccount);

        return creditAccountMapper.toDTO(creditAccount);
    }

    @Override
    public void deleteCreditAccount(Long id) {
        creditAccountRepository.deleteById(id);
    }
}
