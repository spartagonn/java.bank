package com.bank.model;
import java.math.BigDecimal;

public class Account {
    private Long accountId;
    private Long customerId;
    private String accountNumber1; 
    private String accountNumber2; 
    private BigDecimal balance;

    public Account(Long accountId, Long customerId, String accountNumber1, String accountNumber2, BigDecimal balance) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber1 = accountNumber1;
        this.accountNumber2 = accountNumber2;
        this.balance = (balance != null) ? balance : BigDecimal.ZERO;
    }

    public Long getAccountId() { return accountId; }
    public Long getCustomerId() { return customerId; }
    public String getAccountNumber1() { return accountNumber1; }
    public String getAccountNumber2() { return accountNumber2; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
