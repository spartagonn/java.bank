package com.bank.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:./localbank;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customer (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    surname VARCHAR(255),
                    email VARCHAR(255) UNIQUE
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS account (
                    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    customer_id BIGINT,
                    acc_number_1 VARCHAR(50) UNIQUE,
                    acc_number_2 VARCHAR(50) UNIQUE,
                    balance DECIMAL(20, 2) DEFAULT 0.00,
                    FOREIGN KEY (customer_id) REFERENCES customer(id)
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    account_number VARCHAR(50),
                    amount DECIMAL(20, 2),
                    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    type VARCHAR(50)
                )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
