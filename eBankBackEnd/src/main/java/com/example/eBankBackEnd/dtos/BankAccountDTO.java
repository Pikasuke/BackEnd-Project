package com.example.eBankBackEnd.dtos;

import com.example.eBankBackEnd.entities.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDTO {

    private String type;
    private String id;
    private double balance;
    private Date createdDate;
    private AccountStatus status;
    private CustomerDTO customerDTO;
}
