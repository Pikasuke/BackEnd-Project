package com.example.eBankBackEnd.service;

import com.example.eBankBackEnd.dtos.CustomerDTO;
import com.example.eBankBackEnd.entities.*;
import com.example.eBankBackEnd.exception.BalanceNotSufficientEcxeption;
import com.example.eBankBackEnd.exception.BnakAccountNotFoundException;
import com.example.eBankBackEnd.exception.CustomerNotFoundException;
import com.example.eBankBackEnd.mappers.BankAccountMapperImpl;
import com.example.eBankBackEnd.repositories.AccountOperationRepo;
import com.example.eBankBackEnd.repositories.BankAccountRepo;
import com.example.eBankBackEnd.repositories.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class BankAcountServiceImpl implements IBankAccountService {

    private CustomerRepo customerRepo;
    private BankAccountRepo bankAccountRepo;
    private AccountOperationRepo accountOperationRepo;
    private BankAccountMapperImpl dtoMapper;
   // Logger log = LoggerFactory.getILoggerFactory(this.getClass().getName());

    private CustomerRepo customerRepo() {
        return customerRepo;
    }

    private BankAccountRepo bankAccountRepo() {
        return bankAccountRepo;
    }

    private BankAccountMapperImpl dtoMapper() {return dtoMapper;}

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromDTOTOCustomer(customerDTO);
        log.info("Saving new Customer");
        Customer savedCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomerToDTO(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if(customer==null) {
            throw new CustomerNotFoundException("Customer not Found");
        }
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setId((UUID.randomUUID().toString()));
        bankAccount.setCreatedDate(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setOverDraft(overdraft);
        bankAccount.setCustomer(customer);
        CurrentAccount savedAccount = bankAccountRepo.save(bankAccount);
        return savedAccount;
    }

    @Override
    public SavingAccount saveSavingtBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if(customer==null) {
            throw new CustomerNotFoundException("Customer not Found");
        }
        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setId((UUID.randomUUID().toString()));
        bankAccount.setCreatedDate(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setInterestRate(interestRate);
        bankAccount.setCustomer(customer);
        SavingAccount savedAccount = bankAccountRepo.save(bankAccount);
        return savedAccount;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomerToDTO(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BnakAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepo.findById(accountId)
                .orElseThrow(()->new BnakAccountNotFoundException("BanckAcount not found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BnakAccountNotFoundException, BalanceNotSufficientEcxeption {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance()<amount) {
            throw new BalanceNotSufficientEcxeption("Balance not sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepo.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BnakAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepo.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdReceiver, double amount) throws BnakAccountNotFoundException, BalanceNotSufficientEcxeption {
        debit(accountIdSource, amount, "Transfer to " + accountIdReceiver);
        credit(accountIdReceiver, amount, "Tranfer from " + accountIdSource);
    }

    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepo.findAll();
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
       Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomerToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromDTOTOCustomer(customerDTO);
        log.info("Saving new Customer");
        Customer savedCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomerToDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
    }
}
