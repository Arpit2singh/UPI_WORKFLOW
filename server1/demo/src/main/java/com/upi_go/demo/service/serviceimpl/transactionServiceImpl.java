package com.upi_go.demo.service.serviceimpl;
import java.math.BigDecimal;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upi_go.demo.Config.RestConfig;
import com.upi_go.demo.Entities.BankAccountEntity; 
import com.upi_go.demo.Entities.TransactionEntity;
import com.upi_go.demo.service.transactionService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.upi_go.demo.Repository.UserRepository;
import com.upi_go.demo.Entities.TransactionEntity.TransactionStatus;
import com.upi_go.demo.Repository.BankRepository ;
import com.upi_go.demo.Repository.TransactionRepository ;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException ;
import org.springframework.web.client.RestTemplate;


@Service 
public class transactionServiceImpl implements transactionService {
    @Autowired 
    public UserRepository userRepository ;
    @Autowired 
    public BankRepository bankRepository ; 
    @Autowired 
    public TransactionRepository  transactionRepository ;

    @Autowired 
    public RestTemplate restTemplate ;

    @Autowired
    public bankClientServiceImpl bankClientService ;

    
    @Transactional
    @Override
    public TransactionEntity transferMoney(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {
        TransactionEntity transaction = new TransactionEntity() ; 
        Optional <BankAccountEntity> senderBankAccount = bankRepository.findByAccountNumber(senderAccountNumber) ;
        Optional <BankAccountEntity> receiverBankAccount = bankRepository.findByAccountNumber(receiverAccountNumber) ; 
        BankAccountEntity senderAccount = senderBankAccount.orElseThrow(()-> new RuntimeException("Sender Account number not Found")) ; 
        BankAccountEntity receiverAccount = receiverBankAccount.orElseThrow(()-> new RuntimeException("Receiver Account number is not Found")) ; 

        if((senderAccount.getAccountNumber()).equals(receiverAccount.getAccountNumber())){
            throw new RuntimeException("account number is same") ; 
        }
        transaction.setSenderBankAccount(senderAccount);
        transaction.setReceiverBankAccount(receiverAccount);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.SUCCESS);

        
        // boolean isSaved = transactionRepository.save(transaction) != null ;
        // System.out.println("Transaction is saved") ;
      
       BigDecimal senderAmount = senderAccount.getBalance() ; 
       if(senderAmount.subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0){
        throw new RuntimeException("Account Balance is not Sufficient") ; 
       }

    //    @CircuitBreaker(name="bankService", fallbackMethod = "bankFallBack")
        // callBankService() ;
       String response = bankClientService.callBankService() ;
       System.out.println("Response from Bank Service: " + response);
       BigDecimal senderNewAmount = senderAmount.subtract(transaction.getAmount()) ; 
       BigDecimal receiverAmount = transaction.getReceiverBankAccount().getBalance() ; 
       BigDecimal receiverNewAmount = receiverAmount.add(transaction.getAmount()) ;
       transaction.getSenderBankAccount().setBalance(senderNewAmount);
       transaction.getReceiverBankAccount().setBalance(receiverNewAmount);

       bankRepository.save(transaction.getSenderBankAccount());
       bankRepository.save(transaction.getReceiverBankAccount());
       
       transaction.setStatus(TransactionStatus.SUCCESS);
       transaction.setTimestamp(java.time.LocalDateTime.now());
       TransactionEntity savedTransaction = transactionRepository.save(transaction);

        return savedTransaction ;
       
    }

    @Override 
    public TransactionEntity transferMoneyWithRetry(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {
        for(int i= 0 ; i<3 ; i++){
        try{
             return transferMoney(senderAccountNumber, receiverAccountNumber, amount) ;
        }
        catch(ObjectOptimisticLockingFailureException e){

            System.out.println("Retrying transaction due to exception: " + e.getMessage());
            // return transferMoney(senderAccountNumber, receiverAccountNumber, amount) ;
        }
         }
    throw new RuntimeException("Transaction failed after 3 attempts") ; 

    } 
}


