package com.example.eBankBackEnd.repositories;

import com.example.eBankBackEnd.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
