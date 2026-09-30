package com.upi_go.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.upi_go.demo.Entities.TransactionEntity;
import com.upi_go.demo.service.transactionService;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.upi_go.demo.service.EmailService;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;




@RestController
@CrossOrigin
public class TransactionController {
    @Autowired 
    public transactionService transactionService ;   
    
    @Autowired 
    public EmailService emailService ;

    @Autowired
    public RestTemplate restTemplate ; 
    private ExecutorService executor = Executors.newFixedThreadPool(2) ; 
    private ExecutorService emailExecutor = Executors.newFixedThreadPool(2) ;
 


@PostMapping("/pay")
public TransactionEntity  transferMoney(@RequestParam String senderAccountNumber , @RequestParam String receiverAccountNumber , @RequestParam BigDecimal amount){
    return transactionService.transferMoney(senderAccountNumber , receiverAccountNumber , amount) ;
}

@PostMapping("/payAsync")
public List<TransactionEntity> transferAsyncMoney(@RequestParam String senderAccountNumber , @RequestParam String receiverAccountNumber , 
    @RequestParam BigDecimal amount) throws Exception{
   
    Callable<TransactionEntity> payment1 = ()->{
        System.out.println("Thread 1 is executing") ;
    return transactionService.transferMoneyWithRetry(senderAccountNumber , receiverAccountNumber , amount) ; 
    } ;

    Callable<TransactionEntity>payment2 = ()->{
        System.out.println("Thread 2 is executing") ;
        return transactionService.transferMoneyWithRetry(senderAccountNumber, receiverAccountNumber, amount);
    };
    Callable<TransactionEntity>payment3 = ()->{
        System.out.println("Thread 3 is executing") ;
        return transactionService.transferMoneyWithRetry(senderAccountNumber, receiverAccountNumber, amount);
    };

    Callable<TransactionEntity>payment4 = ()->{
        System.out.println("Thread 4 is executing") ;
        return transactionService.transferMoneyWithRetry(senderAccountNumber, receiverAccountNumber, amount);
    };


    Future<TransactionEntity> future1 = executor.submit(payment1) ;
    Future<TransactionEntity> future2 = executor.submit(payment2) ;
    Future<TransactionEntity> future3 = executor.submit(payment3) ;
    Future<TransactionEntity> future4 = executor.submit(payment4) ;

    // TransactionEntity transaction1 = future1.get() ;
    // TransactionEntity transaction2 = future2.get() ;
    // TransactionEntity transaction3 = future3.get() ;
    // TransactionEntity transaction4 = future4.get() ;

    TransactionEntity transaction1 = null ;
    TransactionEntity transaction2 = null ;
    TransactionEntity transaction3 = null ; 
    TransactionEntity transaction4 = null ;

    try{
        transaction1 = future1.get() ;
    }
    catch(Exception e){
       System.out.println("Error in executing payment: " + e.getMessage()) ;
    }

    
    try{
        transaction2 = future2.get() ;
    }
    catch(Exception e){
       System.out.println("Error in executing payment: " + e.getMessage()) ;
       System.err.println("error here ");
    }

    try{
        transaction3 = future3.get() ;
    }
    catch(Exception e){
       System.out.println("Error in executing payment: " + e.getMessage()) ;
    }

    try{
        transaction4 = future4.get() ;
    }
    catch(Exception e){
       System.out.println("Error in executing payment: " + e.getMessage()) ;
    }



    if(transaction1 != null && (transaction1.getStatus() == TransactionEntity.TransactionStatus.SUCCESS || transaction1.getStatus() == TransactionEntity.TransactionStatus.FAILED)){
        TransactionEntity finalTransaction1 = transaction1 ;
         Callable<String> EmailServiceSender = ()->{
         String sendTo = finalTransaction1.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = finalTransaction1.getAmount() ;
         String subject = finalTransaction1.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
         emailExecutor.submit(EmailServiceSender) ;
   
    }
     if( transaction2 != null && (transaction2.getStatus() == TransactionEntity.TransactionStatus.SUCCESS || transaction2.getStatus() == TransactionEntity.TransactionStatus.FAILED)){
           TransactionEntity finalTransaction2 = transaction2 ;
       Callable<String> EmailServiceSender = ()->{
         String sendTo = finalTransaction2.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = finalTransaction2.getAmount() ;
         String subject = finalTransaction2.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
       emailExecutor.submit(EmailServiceSender) ;
    

    }
     if( transaction3 != null && (transaction3.getStatus() == TransactionEntity.TransactionStatus.SUCCESS || transaction3.getStatus() == TransactionEntity.TransactionStatus.FAILED)){
           TransactionEntity finalTransaction3 = transaction3 ;
       Callable<String> EmailServiceSender = ()->{
         String sendTo = finalTransaction3.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = finalTransaction3.getAmount() ;
         String subject = finalTransaction3.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
         emailExecutor.submit(EmailServiceSender) ;
    }

    if( transaction4 != null && (transaction4.getStatus() == TransactionEntity.TransactionStatus.SUCCESS || transaction4.getStatus() == TransactionEntity.TransactionStatus.FAILED)){
           TransactionEntity finalTransaction4 = transaction4 ; 
       Callable<String> EmailServiceSender = ()->{
         String sendTo = finalTransaction4.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = finalTransaction4.getAmount() ;
         String subject = finalTransaction4.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
       emailExecutor.submit(EmailServiceSender) ;
   
    }
    return Arrays.asList(transaction1, transaction2, transaction3, transaction4) ;
} 

}
