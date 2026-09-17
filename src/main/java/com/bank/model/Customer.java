package com.bank.model;

public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String email; 

    public Customer(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
}
