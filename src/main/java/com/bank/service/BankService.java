package com.bank.service;
import com.bank.repository.DatabaseManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankService {
    public void deposit(String accNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Сумма пополнения должна быть больше нуля.");
            return;
        }
        String updateQuery = "UPDATE account SET balance = balance + ? WHERE acc_number_1 = ? OR acc_number_2 = ?";
        String logQuery = "INSERT INTO transactions (account_number, amount, type) VALUES (?, ?, 'DEPOSIT')";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
             PreparedStatement psLog = conn.prepareStatement(logQuery)) {
            psUpdate.setBigDecimal(1, amount);
            psUpdate.setString(2, accNumber);
            psUpdate.setString(3, accNumber);
            if (psUpdate.executeUpdate() > 0) {
                psLog.setString(1, accNumber);
                psLog.setBigDecimal(2, amount);
                psLog.executeUpdate();
                System.out.println("Успешное пополнение на: " + amount);
            } else {
                System.out.println("Счет не найден.");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void withdraw(String accNumber, BigDecimal amount) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String checkTypeQuery = "SELECT balance FROM account WHERE acc_number_1 = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkTypeQuery)) {
                psCheck.setString(1, accNumber);
                ResultSet rs = psCheck.executeQuery();
                if (!rs.next()) {
                    System.out.println("Ошибка: Снятие разрешено только с Депозита 1!");
                    return;
                }
                if (rs.getBigDecimal("balance").compareTo(amount) < 0) {
                    System.out.println("Недостаточно средств.");
                    return;
                }
            }
            String updateQuery = "UPDATE account SET balance = balance - ? WHERE acc_number_1 = ?";
            String logQuery = "INSERT INTO transactions (account_number, amount, type) VALUES (?, ?, 'WITHDRAWAL')";
            try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
                 PreparedStatement psLog = conn.prepareStatement(logQuery)) {
                psUpdate.setBigDecimal(1, amount);
                psUpdate.setString(2, accNumber);
                psUpdate.executeUpdate();
                psLog.setString(1, accNumber);
                psLog.setBigDecimal(2, amount);
                psLog.executeUpdate();
                System.out.println("Успешно снято: " + amount);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void transfer(String fromAcc, String toAcc, BigDecimal amount) {
        if (fromAcc.equals(toAcc)) {
            System.out.println("Нельзя перевести на тот же самый счет.");
            return;
        }
        withdraw(fromAcc, amount);
        deposit(toAcc, amount);
    }
}
