package com.example.online_bank.mapper;

import com.example.online_bank.dto.BankOfficeDTO;
import com.example.online_bank.entity.BankOffice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankOfficeMapper {
    BankOfficeMapper INSTANCE = Mappers.getMapper(BankOfficeMapper.class);

    BankOfficeDTO toDTO(BankOffice bankOffice);

    BankOffice toEntity(BankOfficeDTO bankOfficeDTO);

    // Новый метод для обновления сущности
    void updateEntityFromDTO(BankOfficeDTO bankOfficeDTO, @MappingTarget BankOffice bankOffice);
}
