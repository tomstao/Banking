package Banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String,User> users;
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





}
