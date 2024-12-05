package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private Date birthDate;
    private String placeOfWork;
    private BigDecimal monthlyIncome;
    private int creditRating;
    private List<Long> bankIds;
    private List<Long> creditAccountIds;
    private List<Long> paymentAccountIds;
}