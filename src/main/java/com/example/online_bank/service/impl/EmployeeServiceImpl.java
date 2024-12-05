package com.example.online_bank.service.impl;

import com.example.online_bank.entity.Bank;
import com.example.online_bank.entity.BankOffice;
import com.example.online_bank.entity.Employee;
import com.example.online_bank.repository.EmployeeRepository;
import com.example.online_bank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_bank.dto.EmployeeDTO;
import com.example.online_bank.mapper.EmployeeMapper;
import com.example.online_bank.repository.BankRepository;
import com.example.online_bank.repository.BankOfficeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BankRepository bankRepository,
                               BankOfficeRepository bankOfficeRepository,
                               EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.bankRepository = bankRepository;
        this.bankOfficeRepository = bankOfficeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Bank bank = bankRepository.findById(employeeDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        BankOffice bankOffice = bankOfficeRepository.findById(employeeDTO.getBankOfficeId())
                .orElseThrow(() -> new RuntimeException("Bank office not found"));

        // Создание нового сотрудника
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setBank(bank);
        employee.setBankOffice(bankOffice);

        // Обновление количества сотрудников в банке
        bank.setEmployeeCount(bank.getEmployeeCount() + 1);

        // Сохранение
        employee = employeeRepository.save(employee);
        bankRepository.save(bank);

        return employeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeMapper.toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Обновление полей сотрудника с использованием метода updateEntityFromDTO
        employeeMapper.updateEntityFromDTO(employeeDTO, employee);
        employee = employeeRepository.save(employee);

        return employeeMapper.toDTO(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Bank bank = employee.getBank();

        // Уменьшение количества сотрудников в банке
        bank.setEmployeeCount(bank.getEmployeeCount() - 1);

        // Удаление сотрудника
        employeeRepository.delete(employee);
        bankRepository.save(bank);
    }
}
