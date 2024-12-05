package com.example.online_bank.dto;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class BankDTO {
    private Long id;
    private String name;
    private int officeCount;
    private int atmCount;
    private int employeeCount;
    private int clientCount;
    private int rating;
    private BigDecimal totalMoney;
    private BigDecimal interestRate;
}package com.example.online_bank.mapper;

import com.example.online_bank.dto.BankDTO;
import com.example.online_bank.entity.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bankOffices", ignore = true)
    @Mapping(target = "bankAtms", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Bank toEntity(BankDTO bankDTO);

    BankDTO toDTO(Bank bank);

    void updateEntityFromDTO(BankDTO bankDTO, @MappingTarget Bank bank);
}
