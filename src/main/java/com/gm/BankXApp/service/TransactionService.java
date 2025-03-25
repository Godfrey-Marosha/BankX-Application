package com.gm.BankXApp.service;

import com.gm.BankXApp.dataTransferObject.TransactionRequest;
import com.gm.BankXApp.exception.AccountNotFoundException;
import com.gm.BankXApp.exception.InsufficientFundsException;
import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.model.Transaction;
import com.gm.BankXApp.model.TransactionType;
import com.gm.BankXApp.repository.AccountRepository;
import com.gm.BankXApp.repository.TransactionRepository;
import com.gm.BankXApp.util.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, NotificationService notificationService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void transferFunds(TransactionRequest request) {
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException("From account not found"));
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException("To account not found"));

        BigDecimal amount = request.getAmount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account");
        }

        // Debit from the 'from' account
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        BigDecimal debitCharge = amount.multiply(new BigDecimal("0.0005")); // 0.05% transaction charge
        fromAccount.setBalance(fromAccount.getBalance().subtract(debitCharge));
        accountRepository.save(fromAccount);
        recordTransaction(fromAccount, TransactionType.DEBIT, amount, "Transfer to account " + toAccount.getId());

        // Credit to the 'to' account
        toAccount.setBalance(toAccount.getBalance().add(amount));
        if (toAccount.getAccountType() == com.gm.BankXApp.model.AccountType.SAVINGS) {
            BigDecimal interest = toAccount.getBalance().multiply(new BigDecimal("0.005")); // 0.5% interest
            toAccount.setBalance(toAccount.getBalance().add(interest));
            recordTransaction(toAccount, TransactionType.CREDIT, interest, "Interest payment");
        }
        accountRepository.save(toAccount);
        recordTransaction(toAccount, TransactionType.CREDIT, amount, "Transfer from account " + fromAccount.getId());

        //Send notification
        notificationService.sendNotification(fromAccount.getCustomer().getEmail(), "Transaction Alert", "Funds transfered successfully");
        notificationService.sendNotification(toAccount.getCustomer().getEmail(), "Transaction Alert", "Funds received successfully");
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
