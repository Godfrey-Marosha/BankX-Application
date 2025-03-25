package com.gm.BankXApp.service;

import java.util.ArrayList;
import com.gm.BankXApp.dataTransferObject.CreateCustomerRequest;
import com.gm.BankXApp.model.Customer;
import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.model.AccountType;
import com.gm.BankXApp.repository.CustomerRepository;
import com.gm.BankXApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getMobileNumber());

        customer = customerRepository.save(customer);

        // Create current account
        Account currentAccount = accountService.createAccount(customer, AccountType.CURRENT, BigDecimal.ZERO);

        // Create savings account with joining bonus
        Account savingsAccount = accountService.createAccount(customer, AccountType.SAVINGS, new BigDecimal("500.00"));

        return customer;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(Math.toIntExact(id)).orElse(null); // Handle not found case properly
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        List<Customer> filtered = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer != null) {
                List<Account> accounts = accountRepository.findByCustomerId((long) customer.getId());
                customer.setAccounts(accounts);
                filtered.add(customer);
            }
        }

        return filtered;
    }

}
