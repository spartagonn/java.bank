package com.bank;
import com.bank.repository.DatabaseManager;
import com.bank.service.BankService;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initDatabase();
        BankService bankService = new BankService();
        Scanner scanner = new Scanner(System.in);

        try (var conn = DatabaseManager.getConnection(); var stmt = conn.createStatement()) {
            stmt.execute("MERGE INTO customer KEY(email) VALUES (1, 'Дмитрий', 'Цой', 'tsoy@bank.com')");
            stmt.execute("MERGE INTO account KEY(acc_number_1) VALUES (1, 1, 'DEP1-111', 'DEP2-222', 1000.00)");
        } catch (Exception ignored) {}

        while (true) {
            System.out.println("\n--- БАНКОВСКИЙ ИНТЕРФЕЙС ---");
            System.out.println("Тестовые счета: DEP1 (Со снятием) | DEP2 (Только пополнение)");
            System.out.println("1. Пополнение\n2. Снятие\n3. Перевод\n4. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Номер счета: "); String acc = scanner.nextLine();
                    System.out.print("Сумма: "); BigDecimal amt = scanner.nextBigDecimal();
                    bankService.deposit(acc, amt);
                }
                case 2 -> {
                    System.out.print("Номер счета (Депозит 1): "); String acc = scanner.nextLine();
                    System.out.print("Сумма: "); BigDecimal amt = scanner.nextBigDecimal();
                    bankService.withdraw(acc, amt);
                }
                case 3 -> {
                    System.out.print("С какого счета: "); String from = scanner.nextLine();
                    System.out.print("На какой счет: "); String to = scanner.nextLine();
                    System.out.print("Сумма: "); BigDecimal amt = scanner.nextBigDecimal();
                    bankService.transfer(from, to, amt);
                }
                case 4 -> { return; }
            }
        }
    }
}
