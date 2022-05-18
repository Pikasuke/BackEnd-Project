package com.example.eBankBackEnd.repositories;

import com.example.eBankBackEnd.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepo extends JpaRepository<AccountOperation , Long> {
}
