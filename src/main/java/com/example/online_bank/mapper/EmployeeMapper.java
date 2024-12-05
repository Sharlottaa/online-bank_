package com.example.online_bank.mapper;

import com.example.online_bank.dto.EmployeeDTO;
import com.example.online_bank.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    // Метод для обновления существующей сущности на основе DTO
    void updateEntityFromDTO(EmployeeDTO employeeDTO, @MappingTarget Employee employee);
}
