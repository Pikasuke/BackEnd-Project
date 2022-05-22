package com.example.eBankBackEnd.service;

import com.example.eBankBackEnd.repositories.BankAccountRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {

    public BankAccountRepo bankAccountRepo;

    public BankAccountRepo getBankAccountRepo() {
        return bankAccountRepo;
    }

}
