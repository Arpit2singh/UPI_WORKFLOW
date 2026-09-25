package com.upi_go.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.persistence.Column;
import java.math.BigDecimal;

@Entity 
@Table(name = "bank_accounts") 
public class BankAccountEntity {

@Id 
@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) 
private Long id ;

@Column (unique = true)
private String accountNumber ;


@Version 
private Long version ;

private BigDecimal balance ; 

@OneToOne 
private UserEntity user ;


public void setId(Long id) {
    this.id = id;
}
public Long getId() {
    return id;
}

public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
}
public String getAccountNumber() {
    return accountNumber;
}
public void setBalance(BigDecimal balance) {
    this.balance = balance;
}
public BigDecimal getBalance() {
    return balance;
}

public void setUser(UserEntity user) {
    this.user = user;
}
public UserEntity getUser() {
    return user;
}

public void setVersion(Long version) {
    this.version = version;
}
public Long getVersion() {
    return version;
}
}
