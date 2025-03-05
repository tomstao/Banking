package Banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String,User> accounts;
    static private Bank bank;

    private Bank() {
        this.accounts = new HashMap<>();
    }

    static public Bank getInstance() {
        if (bank == null) bank = new Bank();
        return bank;
    }

    public void registerUser() {
        User newUser = new User();
        accounts.put(newUser.getAccountNumber(), newUser);
        System.out.println("New user registered: " + newUser.getAccountNumber());
    }






}
