package com.gm.BankXApp.dataTransferObject;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BankZTransactionRequest {
    private Long accountId;
    private BigDecimal amount;
    private String transactionType;
}
