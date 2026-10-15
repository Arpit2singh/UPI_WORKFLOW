package com.upi_go.demo.Config ;  
import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

@Configuration 
public class RabbitMQConfig {
    
    @Bean 
    public Queue paymentQueue(){
        return new Queue("payment.queue" , true) ; 
    }
    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}