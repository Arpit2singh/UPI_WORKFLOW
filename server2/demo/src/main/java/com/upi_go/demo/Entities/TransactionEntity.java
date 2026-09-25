package com.upi_go.demo.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import jakarta.persistence.ManyToOne;


@Entity
@Table(name = "transactions")

public class TransactionEntity {


    @Id 
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne
    private BankAccountEntity senderBankAccount ; 

    @ManyToOne
    private BankAccountEntity receiverBankAccount ; 

    private BigDecimal amount  ; 

    public enum TransactionStatus{        
        PENDING,
        SUCCESS,
        FAILED
    } ;

    private TransactionStatus status ;
    private LocalDateTime timestamp ;

    public void setId(long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setSenderBankAccount(BankAccountEntity senderBankAccount) {
        this.senderBankAccount = senderBankAccount;
    }
    public BankAccountEntity getSenderBankAccount() {
        return senderBankAccount;
    }
    public void setReceiverBankAccount(BankAccountEntity receiverBankAccount) {
        this.receiverBankAccount = receiverBankAccount;
    }
    public BankAccountEntity getReceiverBankAccount() {
        return receiverBankAccount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public TransactionStatus getStatus() {
        return status;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
