package com.upi_go.demo.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@CrossOrigin
public class BankController {
    @PostMapping("/bank/transfer")
    public String transfer() {

        double random = Math.random() ; 

        if(random < 0.5){
            return "Bank Transfer Successful";
        }
        if(random < 0.9){
            throw new RuntimeException("Bank Transfer Failed");
        }

        try{
            Thread.sleep(10000) ; 
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt() ;
        }
          return "Bank Transfer Successful After Delay";
    }
}
