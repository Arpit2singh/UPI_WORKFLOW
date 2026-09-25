package com.upi_go.demo.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service 
public class bankClientServiceImpl {
    @Autowired
    public RestTemplate restTemplate ;
    
  
    String url = "http://localhost:8081/bank/transfer" ;
    @CircuitBreaker(name="bankService", fallbackMethod = "bankFallBack")
    String callBankService() {
        return restTemplate.postForObject(url,null , String.class);     
    }

    private String bankFallBack(Exception e){
        throw new RuntimeException("Bank open circuit: " + e.getMessage()) ;
    }



}
