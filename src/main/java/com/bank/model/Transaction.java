package com.bank.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long transactionId;
    private String accountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private String type;

    public Transaction(Long transactionId, String accountNumber, BigDecimal amount, LocalDateTime transactionTime, String type) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionTime = (transactionTime != null) ? transactionTime : LocalDateTime.now();
        this.type = type;
    }

    public Long getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTransactionTime() { return transactionTime; }
    public String getType() { return type; }
}
