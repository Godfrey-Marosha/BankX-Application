package com.gm.BankXApp.dataTransferObject;

import com.gm.BankXApp.model.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetails {
    private Long id;
    private AccountType accountType;
    private BigDecimal balance;
}
