package com.upi_go.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.upi_go.demo.Entities.UserEntity;
import com.upi_go.demo.service.userService;
import java.math.BigDecimal;
import java.util.Map;
import com.upi_go.demo.Entities.BankAccountEntity;

@RestController 
@CrossOrigin 

public class UserController {
    @Autowired 
    private userService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World" ;
    }
    
    @PostMapping("/createUser")
    public UserEntity createUser( @RequestBody  UserEntity user){
        return  userService.createUser(user); 
    }

    @PostMapping ("/createBankAccount")
    public BankAccountEntity createBankAccount(
        @RequestParam Long userId , 
        @RequestParam String accountNumber ,
         @RequestParam BigDecimal balance){
        return  userService.createBankAccount(userId , accountNumber , balance) ; 
    }

    @GetMapping ("/checkBalance")
    public Map<String,Object> checkBalance(
        @RequestParam String accountNumber){
        return userService.checkBalance(accountNumber) ; 
    }

}
