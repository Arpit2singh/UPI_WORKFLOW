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




@RestController
@CrossOrigin
public class TransactionController {
    @Autowired 
    public transactionService transactionService ;   
    
    @Autowired 
    public EmailService emailService ;

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

    TransactionEntity transaction1 = future1.get() ;
    TransactionEntity transaction2 = future2.get() ;
    TransactionEntity transaction3 = future3.get() ;
    TransactionEntity transaction4 = future4.get() ;

    if(future1.get().getStatus() == TransactionEntity.TransactionStatus.SUCCESS || future1.get().getStatus() == TransactionEntity.TransactionStatus.FAILED){
         Callable<String> EmailServiceSender = ()->{
         String sendTo = transaction1.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = transaction1.getAmount() ;
         String subject = transaction1.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
         emailExecutor.submit(EmailServiceSender) ;
   
    }
     if(future2.get().getStatus() == TransactionEntity.TransactionStatus.SUCCESS || future2.get().getStatus() == TransactionEntity.TransactionStatus.FAILED){
       Callable<String> EmailServiceSender = ()->{
         String sendTo = transaction2.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = transaction2.getAmount() ;
         String subject = transaction2.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
       emailExecutor.submit(EmailServiceSender) ;
    

    }
     if(future3.get().getStatus() == TransactionEntity.TransactionStatus.SUCCESS || future3.get().getStatus() == TransactionEntity.TransactionStatus.FAILED){
       Callable<String> EmailServiceSender = ()->{
         String sendTo = transaction3.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = transaction3.getAmount() ;
         String subject = transaction3.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
         emailExecutor.submit(EmailServiceSender) ;
    }
    if(future4.get().getStatus() == TransactionEntity.TransactionStatus.SUCCESS || future4.get().getStatus() == TransactionEntity.TransactionStatus.FAILED){
       Callable<String> EmailServiceSender = ()->{
         String sendTo = transaction4.getReceiverBankAccount().getUser().getEmail() ;
         BigDecimal amountGo = transaction4.getAmount() ;
         String subject = transaction4.getStatus() == TransactionEntity.TransactionStatus.SUCCESS ? "Payment Successful" : "Payment Failed" ;
         emailService.sendEmailGo(sendTo , subject , amountGo) ;
         return "Email Sent" ;
        };
       emailExecutor.submit(EmailServiceSender) ;
   
    }
    return List.of(transaction1, transaction2, transaction3, transaction4) ;
} 

}
