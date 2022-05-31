package com.example.eBankBackEnd.dtos;

import com.example.eBankBackEnd.entities.BankAccount;
import com.example.eBankBackEnd.entities.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private BankAccount bankAccount;
    private String description;
}
