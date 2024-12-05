package com.example.online_bank.service.impl;
import com.example.online_bank.entity.BankAtm;
import com.example.online_bank.dto.BankAtmDTO;
import com.example.online_bank.repository.BankAtmRepository;
import com.example.online_bank.service.BankAtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.example.online_bank.entity.Bank;
import com.example.online_bank.entity.BankOffice;
import com.example.online_bank.mapper.BankAtmMapper;
import com.example.online_bank.repository.BankRepository;
import com.example.online_bank.repository.BankOfficeRepository;

import java.math.BigDecimal;

@Service
public class BankAtmServiceImpl implements BankAtmService {

    private final BankAtmRepository bankAtmRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;
    private final BankAtmMapper bankAtmMapper;

    @Autowired
    public BankAtmServiceImpl(BankAtmRepository bankAtmRepository,
                              BankRepository bankRepository,
                              BankOfficeRepository bankOfficeRepository,
                              BankAtmMapper bankAtmMapper) {
        this.bankAtmRepository = bankAtmRepository;
        this.bankRepository = bankRepository;
        this.bankOfficeRepository = bankOfficeRepository;
        this.bankAtmMapper = bankAtmMapper;
    }

    @Override
    public BankAtmDTO createAtm(BankAtmDTO bankAtmDTO) {
        Bank bank = bankRepository.findById(bankAtmDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        BankOffice office = null;
        if (bankAtmDTO.getOfficeId() != null) {
            office = bankOfficeRepository.findById(bankAtmDTO.getOfficeId())
                    .orElseThrow(() -> new RuntimeException("Office not found"));
        }

        // Генерация суммы денег в банкомате
        BigDecimal moneyAvailable = bank.getTotalMoney().multiply(BigDecimal.valueOf(0.1)); // 10% от банка

        // Создание новой сущности
        BankAtm bankAtm = bankAtmMapper.toEntity(bankAtmDTO);
        bankAtm.setBank(bank);
        bankAtm.setOffice(office);
        bankAtm.setMoneyAvailable(moneyAvailable);

        // Обновление количества банкоматов в банке и офисе
        bank.setAtmCount(bank.getAtmCount() + 1);
        if (office != null) {
            office.setAtmCount(office.getAtmCount() + 1);
        }

        // Сохранение данных
        bankAtm = bankAtmRepository.save(bankAtm);
        bankRepository.save(bank);
        if (office != null) {
            bankOfficeRepository.save(office);
        }

        return bankAtmMapper.toDTO(bankAtm);
    }

    @Override
    public BankAtmDTO getAtmById(Long id) {
        BankAtm bankAtm = bankAtmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ATM not found"));
        return bankAtmMapper.toDTO(bankAtm);
    }

    @Override
    public List<BankAtmDTO> getAllAtms() {
        List<BankAtm> atms = bankAtmRepository.findAll();
        return atms.stream().map(bankAtmMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BankAtmDTO updateAtm(Long id, BankAtmDTO bankAtmDTO) {
        BankAtm bankAtm = bankAtmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ATM not found"));

        // Обновление полей
        bankAtmMapper.updateEntityFromDTO(bankAtmDTO, bankAtm);
        bankAtm = bankAtmRepository.save(bankAtm);

        return bankAtmMapper.toDTO(bankAtm);
    }

    @Override
    public void deleteAtm(Long id) {
        BankAtm bankAtm = bankAtmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ATM not found"));

        Bank bank = bankAtm.getBank();
        BankOffice office = bankAtm.getOffice();

        // Уменьшение количества банкоматов
        bank.setAtmCount(bank.getAtmCount() - 1);
        if (office != null) {
            office.setAtmCount(office.getAtmCount() - 1);
        }

        // Удаление банкомата
        bankAtmRepository.delete(bankAtm);
        bankRepository.save(bank);
        if (office != null) {
            bankOfficeRepository.save(office);
        }
    }
}