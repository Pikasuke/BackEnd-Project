package com.example.eBankBackEnd;

import com.example.eBankBackEnd.dtos.CustomerDTO;
import com.example.eBankBackEnd.entities.*;
import com.example.eBankBackEnd.exception.BalanceNotSufficientEcxeption;
import com.example.eBankBackEnd.exception.BnakAccountNotFoundException;
import com.example.eBankBackEnd.exception.CustomerNotFoundException;
import com.example.eBankBackEnd.repositories.AccountOperationRepo;
import com.example.eBankBackEnd.repositories.BankAccountRepo;
import com.example.eBankBackEnd.repositories.CustomerRepo;
import com.example.eBankBackEnd.service.BankAcountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankBackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (BankAcountServiceImpl bankAcountService) {
		return args -> {
			Stream.of("Toto", "Titi", "Tutu").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAcountService.saveCustomer(customer);
			});
			bankAcountService.listCustomers().forEach(customer -> {
				try {
					bankAcountService.saveCurrentBankAccount(Math.random()+9000,900,customer.getId());
					bankAcountService.saveSavingtBankAccount(Math.random()+9000,4.0,customer.getId());
					List<BankAccount> bankAccounts = bankAcountService.bankAccountList();
					for (BankAccount bankAcccount : bankAccounts) {
						for (int i = 0; i< 10 ; i++) {
							bankAcountService.credit(bankAcccount.getId(), 10+i, "Credit from start "+ i);
							bankAcountService.debit(bankAcccount.getId(),i, "debit from start");
						}
					};
				} catch (CustomerNotFoundException | BnakAccountNotFoundException | BalanceNotSufficientEcxeption e) {
					e.printStackTrace();
				}
			});
		};
	}

	//@Bean
	CommandLineRunner start (CustomerRepo customerRepo,
							 BankAccountRepo bankAccountRepo,
							 AccountOperationRepo accountOperationRepo) {

		return args -> {
			Stream.of("Toto", "Titi", "Tutu").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepo.save(customer);
			});
			customerRepo.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedDate(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(9000);
				bankAccountRepo.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedDate(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(6.0);
				bankAccountRepo.save(savingAccount);
			});

			bankAccountRepo.findAll().forEach(account->{
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setOperationType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
					accountOperation.setBankAccount(account);
					accountOperationRepo.save(accountOperation);
				}
			});
		};

	}

}
