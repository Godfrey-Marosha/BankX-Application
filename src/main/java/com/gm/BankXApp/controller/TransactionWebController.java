package com.gm.BankXApp.controller;

import com.gm.BankXApp.dataTransferObject.TransactionRequest;
import com.gm.BankXApp.model.Account;
import com.gm.BankXApp.service.AccountService;
import com.gm.BankXApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransactionWebController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String showTransferForm(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("transferRequest", new TransactionRequest());
        return "transfer";
    }

    @PostMapping
    public String handleTransfer(@ModelAttribute("transferRequest") TransactionRequest request,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accounts", accountService.getAllAccounts());
            return "transfer";
        }
        try {
            transactionService.transferFunds(request);
            redirectAttributes.addFlashAttribute("successMessage", "Funds transferred successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Transfer failed: " + e.getMessage());
        }
        return "redirect:/transfer";
    }
}
