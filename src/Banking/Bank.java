package Banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String,User> users;
    static private Bank bank;

    private Bank() {
        this.users = new HashMap<>();
    }

    static public Bank getInstance() {
        if (bank == null) bank = new Bank();
        return bank;
    }

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
        if(amount <= 0) return;
        User user = users.get(accountNumber);
        String acountType = user.getAccount().getAccountType();
        if (user != null) {
            user.getAccount().deposit(amount);
        } else {
            System.out.println("Account not found.");
        }

        System.out.println("Deposited " + "+" + amount + " to " + accountNumber +' ' +acountType);
    }

    public void withdraw(String accountNumber, double amount) {
        if(amount <= 0) return;
        User user = users.get(accountNumber);
        if (user != null) {
            if (user.getAccount().getBalance() < amount) {
                System.out.println("Insufficient funds!");
            } else {
                user.getAccount().withdraw(amount);
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


}
