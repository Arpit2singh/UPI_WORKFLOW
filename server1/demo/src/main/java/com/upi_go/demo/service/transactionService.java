package com.upi_go.demo.service;
import java.math.BigDecimal;

import com.upi_go.demo.Entities.TransactionEntity;

public interface transactionService {
    TransactionEntity transferMoney(String senderAccountNumber , String receiverAccountNumber , java.math.BigDecimal amount) ; 
    TransactionEntity transferMoneyWithRetry(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount);

}
