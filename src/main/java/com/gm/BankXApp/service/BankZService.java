package com.gm.BankXApp.service;

import com.gm.BankXApp.dataTransferObject.BankZTransactionRequest;
import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.model.Transaction;
import com.gm.BankXApp.model.TransactionType;
import com.gm.BankXApp.repository.AccountRepository;
import com.gm.BankXApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankZService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankZService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void processBankZTransaction(BankZTransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElse(null);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }

        BigDecimal amount = request.getAmount();
        TransactionType transactionType = TransactionType.valueOf(request.getTransactionType());

        if (transactionType == TransactionType.CREDIT) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            account.setBalance(account.getBalance().subtract(amount));
        }
        accountRepository.save(account);

        recordTransaction(account, transactionType, amount, "Bank Z Transaction");
    }

    @Transactional
    public void processBankZTransactions(List<BankZTransactionRequest> requests) {
        requests.forEach(this::processBankZTransaction);
    }

    private void recordTransaction(Account account, TransactionType type, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription(description);
        transactionRepository.save(transaction);
    }
}