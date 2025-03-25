package com.gm.BankXApp.dataTransferObject;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
