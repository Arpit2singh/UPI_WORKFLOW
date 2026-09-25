package com.upi_go.demo.service.serviceimpl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upi_go.demo.Entities.UserEntity;
import com.upi_go.demo.Repository.UserRepository;
import com.upi_go.demo.service.userService;
import com.upi_go.demo.Entities.BankAccountEntity;
import com.upi_go.demo.Repository.BankRepository;
import java.util.*;
import java.util.Optional;

@Service 
public class userServiceImpl implements userService {

    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private BankRepository bankRepository;


    @Override 
    public UserEntity createUser(UserEntity user) {
       return userRepository.save(user);
    }

    @Override 
    public BankAccountEntity createBankAccount(Long userId , String accountNumber , BigDecimal balance) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new RuntimeException("User not found");
        }
        else{
        UserEntity user = userOptional.get();
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(balance);
        bankAccount.setUser(user);
        return bankRepository.save(bankAccount);
        }
    }

    @Override 
    public Map<String,Object> checkBalance(String accountNumber){
        Map<String , Object>mp = new HashMap<>() ; 
        Optional<BankAccountEntity> bankDetails  = bankRepository.findByAccountNumber(accountNumber) ; 
        if(bankDetails.isPresent()){
            BankAccountEntity bankAccount = bankDetails.get();
            mp.put("Account Number" , bankAccount.getAccountNumber());
            mp.put("Balance" ,  bankAccount.getBalance()); ;  
            return mp ;
        }
        else{
          return Map.of("Error" , "Account not found") ;
        }

        
    }

}
