package com.example.online_bank.mapper;

import com.example.online_bank.dto.PaymentAccountDTO;
import com.example.online_bank.entity.PaymentAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BankMapper.class})
public interface PaymentAccountMapper {
    PaymentAccountDTO toDTO(PaymentAccount paymentAccount);

    @Mapping(target = "user", ignore = true) // Устанавливается в сервисе
    @Mapping(target = "bank", ignore = true) // Устанавливается в сервисе
    PaymentAccount toEntity(PaymentAccountDTO paymentAccountDTO);

    void updateEntityFromDTO(PaymentAccountDTO paymentAccountDTO, @MappingTarget PaymentAccount paymentAccount);
}