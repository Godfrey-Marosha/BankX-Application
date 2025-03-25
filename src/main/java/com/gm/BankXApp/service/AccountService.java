package com.gm.BankXApp.service;

import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.model.Customer;
import com.gm.BankXApp.model.AccountType;
import com.gm.BankXApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Customer customer, AccountType accountType, BigDecimal initialBalance) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setBalance(initialBalance);
        account.setCreatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
