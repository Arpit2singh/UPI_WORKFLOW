package com.upi_go.demo.service;
import java.math.BigDecimal;


public interface EmailService {
     void sendEmailGo(String toEmail, String subject, BigDecimal amount);
    
}
