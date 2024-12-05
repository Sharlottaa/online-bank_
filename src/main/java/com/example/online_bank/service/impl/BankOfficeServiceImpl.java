package com.example.online_bank.service.impl;

import com.example.online_bank.entity.BankOffice;
import com.example.online_bank.repository.BankOfficeRepository;
import com.example.online_bank.service.BankOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.online_bank.entity.Bank;
import com.example.online_bank.repository.BankRepository;
import java.util.List;

import com.example.online_bank.dto.BankOfficeDTO;
import com.example.online_bank.mapper.BankOfficeMapper;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class BankOfficeServiceImpl implements BankOfficeService {

    private final BankOfficeRepository bankOfficeRepository;
    private final BankRepository bankRepository;
    private final BankOfficeMapper bankOfficeMapper;

    @Autowired
    public BankOfficeServiceImpl(BankOfficeRepository bankOfficeRepository,
                                 BankRepository bankRepository,
                                 BankOfficeMapper bankOfficeMapper) {
        this.bankOfficeRepository = bankOfficeRepository;
        this.bankRepository = bankRepository;
        this.bankOfficeMapper = bankOfficeMapper;
    }

    @Override
    public BankOfficeDTO createOffice(BankOfficeDTO bankOfficeDTO) {
        Bank bank = bankRepository.findById(bankOfficeDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        // Генерация доступных денег в офисе (10% от общего объема денег банка)
        BigDecimal moneyAvailable = bank.getTotalMoney().multiply(BigDecimal.valueOf(0.1));

        // Расчет стоимости аренды
        BigDecimal rentalCost = moneyAvailable.multiply(BigDecimal.valueOf(0.05)); // например, 5% от доступных средств

        // Создание нового офиса
        BankOffice bankOffice = bankOfficeMapper.toEntity(bankOfficeDTO);
        bankOffice.setBank(bank);
        bankOffice.setMoneyAvailable(moneyAvailable);
        bankOffice.setRentalCost(rentalCost);

        // Обновление количества офисов в банке
        bank.setOfficeCount(bank.getOfficeCount() + 1);

        // Сохранение
        bankOffice = bankOfficeRepository.save(bankOffice);
        bankRepository.save(bank);

        return bankOfficeMapper.toDTO(bankOffice);
    }

    @Override
    public BankOfficeDTO getOfficeById(Long id) {
        BankOffice bankOffice = bankOfficeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));
        return bankOfficeMapper.toDTO(bankOffice);
    }

    @Override
    public List<BankOfficeDTO> getAllOffices() {
        List<BankOffice> offices = bankOfficeRepository.findAll();
        return offices.stream().map(bankOfficeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BankOfficeDTO updateOffice(Long id, BankOfficeDTO bankOfficeDTO) {
        BankOffice bankOffice = bankOfficeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        // Обновление полей
        bankOfficeMapper.updateEntityFromDTO(bankOfficeDTO, bankOffice);

        // Сохраняем обновленный офис
        bankOffice = bankOfficeRepository.save(bankOffice);

        return bankOfficeMapper.toDTO(bankOffice);
    }

    @Override
    public void deleteOffice(Long id) {
        BankOffice bankOffice = bankOfficeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        Bank bank = bankOffice.getBank();

        // Уменьшение количества офисов в банке
        bank.setOfficeCount(bank.getOfficeCount() - 1);

        bankOfficeRepository.delete(bankOffice);
        bankRepository.save(bank);
    }
}
