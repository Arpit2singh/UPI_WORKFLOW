package com.upi_go.demo.service.serviceimpl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import com.upi_go.demo.service.EmailService;
import software.amazon.awssdk.regions.Region;

@Service
public class EmailServiceImpl implements EmailService{
    
    private final SesClient  sesClient = SesClient.builder().
                              region(Region.AP_SOUTH_1). 
                              build() ;     
    private final String SENDER = "arpit.io.dev@gmail.com" ; 

    @Override 
    public void sendEmailGo(String toEmail, String subject, BigDecimal amount){
         sendEmail(toEmail, subject , amount);
    }
 
    
    private void sendEmail(String toEmail, String subject, BigDecimal amount){
        try {
        SendEmailRequest request = SendEmailRequest.builder()
        .source(SENDER)
        .destination(Destination.builder().toAddresses(toEmail).build())
        .message(Message.builder()
                .subject(Content.builder().data(subject).build())
                .body(Body.builder()
                        .text(Content.builder().data("transaction of " + amount).build())
                        .build())
                .build())
        .build();
            sesClient.sendEmail(request);
            System.out.println("Email sent to " + toEmail);
        } catch (Exception e) {
          System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}
