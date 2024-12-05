package com.example.online_bank.mapper;

import com.example.online_bank.dto.CreditAccountDTO;
import com.example.online_bank.entity.CreditAccount;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CreditAccountMapper {
    CreditAccountMapper INSTANCE = Mappers.getMapper(CreditAccountMapper.class);

    CreditAccountDTO toDTO(CreditAccount creditAccount);

    CreditAccount toEntity(CreditAccountDTO creditAccountDTO);

    // Метод для обновления существующей сущности
    void updateEntityFromDTO(CreditAccountDTO creditAccountDTO, @MappingTarget CreditAccount creditAccount);
}
