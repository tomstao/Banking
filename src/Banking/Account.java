package Banking;

import java.util.HashMap;
import java.util.Map;

public abstract class Account {
    protected final String accountNumber;
    protected double balance;
    private final Map<String, Transaction> transactions;


    protected Account() {
        this.transactions = new HashMap<>();
        this.accountNumber = Utility.AccountNumberGenerator(10);
        this.balance = 0.0;
    }

    public abstract String getAccountType();

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    static Account createAccountForUser(User user, String accountType) {
        if (user == null) {
            throw new IllegalArgumentException("Only a User can create an Account.");
        }
        if (accountType.equalsIgnoreCase("Savings")) {
            return new SavingsAccount();
        } else if (accountType.equalsIgnoreCase("Checking")) {
            return new CheckingAccount();
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    public void printTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\nTransaction History for Account " + accountNumber);
            for (Transaction transaction : transactions.values()) {
                transaction.printTransaction();
            }
        }
    }
}

class SavingsAccount extends Account {
    public String getAccountType() {
        return "Savings";
    }
}

class CheckingAccount extends Account {
    public String getAccountType() {
        return "Checking";
    }
}