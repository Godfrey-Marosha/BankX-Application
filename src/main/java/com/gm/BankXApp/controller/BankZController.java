package com.gm.BankXApp.controller;

import com.gm.BankXApp.dataTransferObject.BankZTransactionRequest;
import com.gm.BankXApp.service.BankZService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bankz")
public class BankZController {

    private final BankZService bankZService;

    @Autowired
    public BankZController(BankZService bankZService) {
        this.bankZService = bankZService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Void> processBankZTransaction(@RequestBody BankZTransactionRequest request) {
        bankZService.processBankZTransaction(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> processBankZTransactions(@RequestBody List<BankZTransactionRequest> requests) {
        bankZService.processBankZTransactions(requests);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
