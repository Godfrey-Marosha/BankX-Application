package com.gm.BankXApp.controller;

import com.gm.BankXApp.model.Customer;
import com.gm.BankXApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    private final CustomerService customerService;

    public WebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String showDashboard(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "index";
    }
}

