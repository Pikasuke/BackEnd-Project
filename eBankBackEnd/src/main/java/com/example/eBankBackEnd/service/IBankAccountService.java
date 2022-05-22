package com.example.eBankBackEnd.service;

import com.example.eBankBackEnd.dtos.CustomerDTO;
import com.example.eBankBackEnd.entities.BankAccount;
import com.example.eBankBackEnd.entities.CurrentAccount;
import com.example.eBankBackEnd.entities.Customer;
import com.example.eBankBackEnd.entities.SavingAccount;
import com.example.eBankBackEnd.exception.BalanceNotSufficientEcxeption;
import com.example.eBankBackEnd.exception.BnakAccountNotFoundException;
import com.example.eBankBackEnd.exception.CustomerNotFoundException;

import java.util.Currency;
import java.util.List;

public interface IBankAccountService {

    CustomerDTO saveCustomer (CustomerDTO customerDTO);
    CurrentAccount saveCurrentBankAccount (double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingtBankAccount (double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccount getBankAccount (String accountId) throws BnakAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BnakAccountNotFoundException, BalanceNotSufficientEcxeption;
    void credit(String accountId, double amount, String description) throws BnakAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdReceiver, double amount) throws BnakAccountNotFoundException, BalanceNotSufficientEcxeption;
    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
