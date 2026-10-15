package com.upi_go.demo.ExceptionHandler.ExceptionHandlerImpl;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.upi_go.demo.ExceptionHandler.paymentException;
@RestControllerAdvice 
public class paymentExceptionImpl {
    
    @ExceptionHandler(paymentException.class) 
    public String handlePaymentException(paymentException ex){
        return ex.getMessage() ; 
    }
}
