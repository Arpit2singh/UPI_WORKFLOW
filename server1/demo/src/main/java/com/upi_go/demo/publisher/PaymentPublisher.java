package com.upi_go.demo.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upi_go.demo.Config.RabbitMQConfig ; 
import org.springframework.amqp.rabbit.core.RabbitTemplate ;
import com.upi_go.demo.Dto.response.PaymentMessage ;
import com.upi_go.demo.Entities.TransactionEntity;
@Service 
public class PaymentPublisher {

    @Autowired 
    private  RabbitTemplate rabbitTemplate ;

    public void publishPayement(PaymentMessage message){
      rabbitTemplate.convertAndSend("payment.queue" , message) ; 
    }

}
