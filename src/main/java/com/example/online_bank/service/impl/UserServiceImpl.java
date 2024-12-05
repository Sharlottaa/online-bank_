package com.example.online_bank.service.impl;

import com.example.online_bank.entity.User;
import com.example.online_bank.exception.ResourceNotFoundException;
import com.example.online_bank.repository.UserRepository;
import com.example.online_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.online_bank.dto.UserDTO;
import com.example.online_bank.mapper.UserMapper;

import java.util.Optional;

import com.example.online_bank.entity.Bank;
import com.example.online_bank.entity.CreditAccount;
import com.example.online_bank.entity.PaymentAccount;
import com.example.online_bank.repository.BankRepository;
import com.example.online_bank.repository.CreditAccountRepository;
import com.example.online_bank.repository.PaymentAccountRepository;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BankRepository bankRepository,
                           CreditAccountRepository creditAccountRepository,
                           PaymentAccountRepository paymentAccountRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.creditAccountRepository = creditAccountRepository;
        this.paymentAccountRepository = paymentAccountRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        // Установка связей с банками
        if (userDTO.getBankIds() != null && !userDTO.getBankIds().isEmpty()) {
            List<Bank> banks = bankRepository.findAllById(userDTO.getBankIds());
            user.setBanks(banks);
        }

        // Установка связей с кредитными счетами
        if (userDTO.getCreditAccountIds() != null && !userDTO.getCreditAccountIds().isEmpty()) {
            List<CreditAccount> creditAccounts = creditAccountRepository.findAllById(userDTO.getCreditAccountIds());
            user.setCreditAccounts(creditAccounts);
        }

        // Установка связей с платежными счетами
        if (userDTO.getPaymentAccountIds() != null && !userDTO.getPaymentAccountIds().isEmpty()) {
            List<PaymentAccount> paymentAccounts = paymentAccountRepository.findAllById(userDTO.getPaymentAccountIds());
            user.setPaymentAccounts(paymentAccounts);
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Обновление полей сущности User на основе DTO
        userMapper.updateEntityFromDTO(userDTO, existingUser);

        // Обновление связей с банками
        if (userDTO.getBankIds() != null) {
            List<Bank> banks = bankRepository.findAllById(userDTO.getBankIds());
            existingUser.setBanks(banks);
        }

        // Обновление связей с кредитными счетами
        if (userDTO.getCreditAccountIds() != null) {
            List<CreditAccount> creditAccounts = creditAccountRepository.findAllById(userDTO.getCreditAccountIds());
            existingUser.setCreditAccounts(creditAccounts);
        }

        // Обновление связей с платежными счетами
        if (userDTO.getPaymentAccountIds() != null) {
            List<PaymentAccount> paymentAccounts = paymentAccountRepository.findAllById(userDTO.getPaymentAccountIds());
            existingUser.setPaymentAccounts(paymentAccounts);
        }

        existingUser = userRepository.save(existingUser);
        return userMapper.toDTO(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}