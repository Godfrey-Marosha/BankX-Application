package com.gm.BankXApp.repository;

import com.gm.BankXApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository <Account, Long> {
    List<Account> findByCustomerId(Long customerId);
}
