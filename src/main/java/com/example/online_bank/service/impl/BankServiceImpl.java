package com.example.online_bank.service.impl;

import com.example.online_bank.entity.Bank;
import com.example.online_bank.exception.ResourceNotFoundException;
import com.example.online_bank.repository.BankRepository;
import com.example.online_bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.online_bank.dto.BankDTO;
import com.example.online_bank.mapper.BankMapper;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    @Override
    public BankDTO createBank(BankDTO bankDTO) {
        // Генерация случайных значений для новых банков
        Random random = new Random();

        // Генерация случайного рейтинга от 0 до 100
        int rating = random.nextInt(101);

        // Генерация случайной суммы в банке (от 0 до 1 000 000)
        BigDecimal totalMoney = BigDecimal.valueOf(random.nextInt(1_000_001));

        // Генерация процентной ставки (от 0 до 20%)
        BigDecimal interestRate = BigDecimal.valueOf(random.nextInt(21)).divide(BigDecimal.valueOf(100));

        // Заполнение DTO новыми значениями
        bankDTO.setRating(rating);
        bankDTO.setTotalMoney(totalMoney);
        bankDTO.setInterestRate(interestRate);

        // Преобразование DTO в сущность
        Bank bank = bankMapper.toEntity(bankDTO);

        // Сохранение банка в базу данных
        bank = bankRepository.save(bank);

        // Возвращаем DTO после создания
        return bankMapper.toDTO(bank);
    }

    @Override
    public BankDTO getBankById(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank not found"));
        return bankMapper.toDTO(bank);
    }

    @Override
    public List<BankDTO> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks.stream().map(bankMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BankDTO updateBank(Long id, BankDTO bankDTO) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank not found"));

        // Генерация случайных значений для обновления (по аналогии с createBank)
        Random random = new Random();

        int rating = random.nextInt(101);
        BigDecimal totalMoney = BigDecimal.valueOf(random.nextInt(1_000_001));
        BigDecimal interestRate = BigDecimal.valueOf(random.nextInt(21)).divide(BigDecimal.valueOf(100));

        bankDTO.setRating(rating);
        bankDTO.setTotalMoney(totalMoney);
        bankDTO.setInterestRate(interestRate);

        // Обновление сущности
        bankMapper.updateEntityFromDTO(bankDTO, bank);
        bank = bankRepository.save(bank);
        return bankMapper.toDTO(bank);
    }

    @Override
    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
}