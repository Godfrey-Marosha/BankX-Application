package com.gm.BankXApp.controller;

import com.gm.BankXApp.dataTransferObject.CreateCustomerRequest;
import com.gm.BankXApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerWebController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/create")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new CreateCustomerRequest());
        return "customer_form";
    }

    @PostMapping("/create")
    public String createCustomer(
            @ModelAttribute("customer") CreateCustomerRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "customer_form";
        }

        customerService.createCustomer(request);
        redirectAttributes.addFlashAttribute("successMessage", "Customer created successfully!");
        return "redirect:/";
    }
}
