package Banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {
    private final Map<String,User> usersChecking;
    private final Map<String,User> userSaving;

    public Bank() {
        usersChecking = new HashMap<>();
        userSaving = new HashMap<>();
    }

    //    for singleton design.
//    static public Bank getInstance() {
//        if (bank == null) bank = new Bank();
//        return bank;
//    }

    public User registerUser() {
        User newUser = new User();
        usersChecking.put(newUser.getCheckingAccountNumber(), newUser);
        userSaving.put(newUser.getSavingAccountNumber(), newUser);

        System.out.println("New user registered: " + newUser.getFirstName() + " " + newUser.getLastName());
        return newUser;
    }

    public User authenticateUser(String accountNumber, String password) {
        if (!usersChecking.containsKey(accountNumber) && !userSaving.containsKey(accountNumber)) {
            return null;
        }
        User user = usersChecking.containsKey(accountNumber) ? usersChecking.get(accountNumber) : userSaving.get(accountNumber);
        if (user != null && user.verifyPassword(password)) {
            System.out.println("Login successful! Welcome " + user.getFirstName());
            return user;
        } else {
            System.out.println("Invalid account number or password.");
            return null;
        }
    }

    public void deposit(Account account, double amount) {
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("Enter the deposit amount: ");
            if (amount <= 0) {
                System.out.println("Invalid amount. Do you wish to continue?");
                String choice = Utility.getValidString("Press 1 to 1, or other key to exist");
                if (choice.equals("1")) continue;
                else return;

            }


                User user = usersChecking.get(account.getAccountNumber());
                account.deposit(amount);
                generateTransaction(user, account, amount);
                System.out.println("Deposited +" + amount + " to " + account.getAccountNumber() + " (" + account.getAccountType() + ")");



        }
    }

    public void withdraw(Account account) {
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("Enter the withdrawal amount: ");
            double amount = scanner.nextDouble();
            if (amount >= account.getBalance()) {
                System.out.println("Invalid amount. Do you wish to continue?");
                String choice = Utility.getValidString("Press Y to continue, or other key to exist");
                if (choice.equals("Y")) continue;
                else {
                    System.out.println("Exiting withdrawal...");
                    return;
                }
            }
            User user = usersChecking.get(account.getAccountNumber());
            account.withdraw(amount);
            generateTransaction(user, account, amount);
            System.out.println("Deposited +" + amount + " to " + account.getAccountNumber() + " (" + account.getAccountType() + ")");
        }


    }

    public void checkBalance(Account account) {
        System.out.println("Current balance: " + account.getBalance());
    }

    public void listAccounts() {
        if (usersChecking.isEmpty() &&  userSaving.isEmpty()) {
            System.out.println("No users registered yet.");
        } else {
            System.out.println("\n==== Bank Users & Accounts ====");
            for (User user : usersChecking.values()) {
                SavingsAccount savingAccount = user.getSavingAccount();
                CheckingAccount checkingAccount = user.getCheckingAccount();
                String savingAccountNumber = savingAccount.getAccountNumber();
                String checkingAccountNumber = checkingAccount.getAccountNumber();
                double savingAccountBalance = savingAccount.getBalance();
                double checkingAccountBalance = checkingAccount.getBalance();
                System.out.println(user.getFirstName() + " " + user.getLastName() +
                        " - Checking Account Number: " + checkingAccountNumber + ", balance: " + savingAccountBalance + "\n" +
                        " - Saving Account Number: " + savingAccountNumber + ", balance: " + checkingAccountBalance + "\n");
            }
        }
    }

    private void generateTransaction(User user, Account account, double amount) {
        Transaction ts = new Transaction(account.getAccountNumber(), account.getAccountType(), amount);
        account.addTransaction(ts);  // Store transactions in the Account class
    }


}
