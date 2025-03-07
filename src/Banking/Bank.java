package Banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, User> usersChecking;
    private final Map<String, User> usersSaving;
    private final Map<String, User> usersByID;

    public Bank() {
        usersChecking = new HashMap<>();
        usersSaving = new HashMap<>();
        usersByID = new HashMap<>();
    }

    public User registerUser() {
        System.out.println("===== Register New User =====");
        String firstName = Utility.getValidString("Enter first name: ");
        String lastName = Utility.getValidString("Enter last name: ");
        String userID = Utility.getValidString("Enter user ID: ");
        String password = Utility.getValidString("Enter password: ");

        User newUser = new User();
        usersChecking.put(newUser.getCheckingAccountNumber(), newUser);
        usersSaving.put(newUser.getSavingAccountNumber(), newUser);
        usersByID.put(userID, newUser);

        System.out.println("New user registered: " + newUser.getFirstName() + " " + newUser.getLastName());
        return newUser;
    }

    public User authenticateUser(String accountNumber, String password) {
        User user = getUserByAccountNumber(accountNumber);
        if (user != null && user.verifyPassword(password)) {
            System.out.println("Login successful! Welcome " + user.getFirstName());
            return user;
        }
        System.out.println("Invalid account number or password.");
        return null;
    }

    public void deposit(Account account) {
        while (true) {
            double amount = Utility.getValidInt("Enter the deposit amount: ");
            if (amount <= 0) {
                System.out.println("Invalid amount. Do you wish to continue?");
                String choice = Utility.getValidString("Press 1 to retry, or other key to exit: ");
                if (!choice.equals("1")) return; // Exit deposit
            } else {
                account.deposit(amount);
                generateTransaction(getUserByAccountNumber(account.getAccountNumber()), account, amount);
                System.out.println("Deposited +" + amount + " to " + account.getAccountNumber() + " (" + account.getAccountType() + ")");
                return; // Successful deposit, exit loop
            }
        }
    }

    public void withdraw(Account account) {
        while (true) {
            double amount = Utility.getValidInt("Enter the withdrawal amount: ");
            if (amount > account.getBalance()) {
                System.out.println("Insufficient funds. Do you wish to continue?");
                String choice = Utility.getValidString("Press Y to retry, or other key to exit: ");
                if (!choice.equalsIgnoreCase("Y")) {
                    System.out.println("Exiting withdrawal...");
                    return;
                }
            } else {
                account.withdraw(amount);
                generateTransaction(getUserByAccountNumber(account.getAccountNumber()), account, amount);
                System.out.println("Withdrawn -" + amount + " from " + account.getAccountNumber() + " (" + account.getAccountType() + ")");
                return; // Successful withdrawal, exit loop
            }
        }
    }

    public void checkBalance(Account account) {
        System.out.println("Current balance: $" + account.getBalance());
    }

    public void listAccounts() {
        if (usersChecking.isEmpty() && usersSaving.isEmpty()) {
            System.out.println("No users registered yet.");
            return;
        }

        System.out.println("\n==== Bank Users & Accounts ====");
        for (User user : usersChecking.values()) {
            System.out.println(user.getFirstName() + " " + user.getLastName() +
                    "\n - Checking Account: " + user.getCheckingAccountNumber() + ", Balance: $" + user.getCheckingAccount().getBalance() +
                    "\n - Saving Account: " + user.getSavingAccountNumber() + ", Balance: $" + user.getSavingAccount().getBalance());
        }
    }

    private void generateTransaction(User user, Account account, double amount) {
        if (user != null) {
            Transaction ts = new Transaction(account.getAccountNumber(), account.getAccountType(), amount);
            account.addTransaction(ts);
        }
    }

    private User getUserByAccountNumber(String accountNumber) {
        return usersChecking.getOrDefault(accountNumber, usersSaving.get(accountNumber));
    }
}