package com.gm.BankXApp.controller;

import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountWebController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/details/{id}")
    public String viewAccountDetails(@PathVariable Long id, Model model) {
        System.out.println("Getting account with ID: " + id);
        Account account = accountService.getAccountById(id);
        if (account == null) {
            System.out.println("Account not found!");
            return "redirect:/?error=AccountNotFound";
        }
        System.out.println("Account found: " + account.getAccountType());
        model.addAttribute("account", account);
        return "account_details";
    }

    @GetMapping("/test")
    public String testRoute() {
        System.out.println("Test route hit!");
        return "account_details";
    }
}
