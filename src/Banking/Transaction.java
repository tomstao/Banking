package Banking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private final String accountNumber;
    private final String type;
    private final double amount;
    private final String timestamp;

    public Transaction(String accountNumber, String type, double amount) {
        this.transactionId = generateTransactionId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = generateTimestamp();
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();  // Unique transaction ID
    }

    private String generateTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void printTransaction() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Type: " + type);
        System.out.println("Amount: $" + amount);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("------------------------------------");
    }

    public String getTransactionId() {
        return transactionId;
    }
}