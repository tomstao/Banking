package Banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String,User> users;
    static private Bank bank;

    public Bank() {
        this.users = new HashMap<>();
    }

//    for singleton design.
//    static public Bank getInstance() {
//        if (bank == null) bank = new Bank();
//        return bank;
//    }

    public void registerUser() {
        User newUser = new User();
        users.put(newUser.getAccountNumber(), newUser);
        System.out.println("New user registered: " + newUser.getAccountNumber());
    }

    public User authenticateUser(String accountNumber, String password) {
        User user = users.get(accountNumber);
        if (user != null && user.verifyPassword(password)) {
            System.out.println("Login successful! Welcome " + user.getFirstName());
            return user;
        } else {
            System.out.println("Invalid account number or password.");
            return null;
        }
    }

    public void deposit(String accountNumber, double amount) {
        if (amount <= 0) return;

        User user = users.get(accountNumber);
        if (user == null) {
            System.out.println("Account not found.");
            return;
        }

        String accountType = user.getAccount().getAccountType();
        user.getAccount().deposit(amount);
        generateTransaction(user, accountNumber, accountType, amount);

        System.out.println("Deposited +" + amount + " to " + accountNumber + " (" + accountType + ")");
    }

    public void withdraw(String accountNumber, double amount) {
        if(amount <= 0) return;
        User user = users.get(accountNumber);
        String accountType = user.getAccount().getAccountType();
        if (user != null) {
            if (user.getAccount().getBalance() < amount) {
                System.out.println("Insufficient funds!");
            } else {
                user.getAccount().withdraw(amount);
                generateTransaction(user, accountNumber, accountType,amount);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void checkBalance(String accountNumber) {
        User user = users.get(accountNumber);
        if (user != null) {
            System.out.println("Balance: " + user.getAccount().getBalance());
        }
    }

    public void listAccounts() {
        if (users.isEmpty()) {
            System.out.println("No users registered yet.");
        } else {
            System.out.println("\n==== Bank Users & Accounts ====");
            for (User user : users.values()) {
                System.out.println(user.getFirstName() + " " + user.getLastName() +
                        " - Account Number: " + user.getAccountNumber() +
                        " - Balance: $" + user.getAccount().getBalance());
            }
        }
    }

    private void generateTransaction(User user, String accountNumber, String accountType, double amount) {
        Transaction ts = new Transaction(accountNumber, accountType, amount);
        user.getAccount().addTransaction(ts);  // Store transactions in the Account class
    }

}
