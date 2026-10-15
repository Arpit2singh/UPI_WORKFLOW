package com.upi_go.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.upi_go.demo.service.transactionService ;
import com.upi_go.demo.Dto.response.PaymentMessage ;    
import com.upi_go.demo.Entities.TransactionEntity;

@Component 
public class PaymentConsumer {
    
    @Autowired 
    private transactionService transactionService ; 
    @RabbitListener(queues="payment.queue" , concurrency = "6")
    public void consumePayment(PaymentMessage message){
    System.out.println("Payment message received : " +  Thread.currentThread().getName() + " - " + 
    message.getSenderAccountNumber() + " to " + message.getReceiverAccountNumber() + " amount : " + message.getAmount()) ;
     transactionService.transferMoney(message.getSenderAccountNumber() , message.getReceiverAccountNumber() , message.getAmount()) ;
    }

}
