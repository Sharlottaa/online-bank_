package com.example.online_bank.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeDTO {
    private Long id;
    private String fullName;
    private Date birthDate;
    private String position;
    private boolean isRemote;
    private boolean canIssueLoans;
    private BigDecimal salary;
    private Long bankId;
    private Long bankOfficeId;
}
