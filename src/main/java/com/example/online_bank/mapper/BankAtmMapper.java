package com.example.online_bank.mapper;

import com.example.online_bank.dto.BankAtmDTO;
import com.example.online_bank.entity.BankAtm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankAtmMapper {

    BankAtmDTO toDTO(BankAtm bankAtm);

    BankAtm toEntity(BankAtmDTO bankAtmDTO);

    // Метод для обновления сущности из DTO
    void updateEntityFromDTO(BankAtmDTO bankAtmDTO, @MappingTarget BankAtm bankAtm);
}
