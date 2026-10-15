package com.upi_go.demo.Dto.response;

import java.math.BigDecimal;

public class PaymentMessage {
    private String SenderAccountNumber;
    private String ReceiverAccountNumber;
    private BigDecimal amount;

    public void setSenderAccountNumber(String senderAccountNumber) {
        SenderAccountNumber = senderAccountNumber;
    }
    public void setReceiverAccountNumber(String receiverAccountNumber) {
        ReceiverAccountNumber = receiverAccountNumber;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getSenderAccountNumber() {
        return SenderAccountNumber;
    }
    public String getReceiverAccountNumber() {
        return ReceiverAccountNumber;
    }
}
