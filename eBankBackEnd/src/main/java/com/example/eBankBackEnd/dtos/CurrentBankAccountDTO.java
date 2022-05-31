package com.example.eBankBackEnd.dtos;

import com.example.eBankBackEnd.entities.AccountOperation;
import com.example.eBankBackEnd.entities.AccountStatus;
import com.example.eBankBackEnd.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBankAccountDTO extends BankAccountDTO{

    private double overdraft;
}
