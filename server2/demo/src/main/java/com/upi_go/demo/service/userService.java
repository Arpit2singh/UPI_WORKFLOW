package com.upi_go.demo.service;
import org.springframework.stereotype.Service;
import com.upi_go.demo.Entities.BankAccountEntity;
import com.upi_go.demo.Entities.UserEntity;
import java.util.*;
import java.math.BigDecimal;

public interface userService {
    UserEntity createUser(UserEntity user);
    BankAccountEntity createBankAccount(Long userId , String accountNumber , BigDecimal balance) ;
    Map<String,Object> checkBalance(String accountNumber) ;
}
