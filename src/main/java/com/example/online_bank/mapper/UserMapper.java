package com.example.online_bank.mapper;

import com.example.online_bank.dto.UserDTO;
import com.example.online_bank.entity.User;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import com.example.online_bank.entity.Bank;
import com.example.online_bank.entity.CreditAccount;
import com.example.online_bank.entity.PaymentAccount;
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    /**
     * Метод для обновления существующей сущности User на основе данных из UserDTO.
     */
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget User user);

    @AfterMapping
    default void afterToDTO(User user, @MappingTarget UserDTO userDTO) {
        if (user.getBanks() != null) {
            List<Long> bankIds = user.getBanks().stream()
                    .map(Bank::getId)
                    .collect(Collectors.toList());
            userDTO.setBankIds(bankIds);
        }

        if (user.getCreditAccounts() != null) {
            List<Long> creditAccountIds = user.getCreditAccounts().stream()
                    .map(CreditAccount::getId)
                    .collect(Collectors.toList());
            userDTO.setCreditAccountIds(creditAccountIds);
        }

        if (user.getPaymentAccounts() != null) {
            List<Long> paymentAccountIds = user.getPaymentAccounts().stream()
                    .map(PaymentAccount::getId)
                    .collect(Collectors.toList());
            userDTO.setPaymentAccountIds(paymentAccountIds);
        }
    }
}