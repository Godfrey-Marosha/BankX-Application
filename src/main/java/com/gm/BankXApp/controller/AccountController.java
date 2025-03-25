package com.gm.BankXApp.controller;

import com.gm.BankXApp.dataTransferObject.AccountDetails;
import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetails> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setId(Long.valueOf(account.getId()));
            accountDetails.setAccountType(account.getAccountType());
            accountDetails.setBalance(account.getBalance());
            return new ResponseEntity<>(accountDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}