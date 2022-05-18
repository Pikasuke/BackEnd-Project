package com.example.eBankBackEnd.repositories;

import com.example.eBankBackEnd.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository <BankAccount, String> {
}
