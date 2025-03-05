package Banking;

public abstract class Account {
    protected final String accountNumber;
    protected double balance;

    protected Account() {
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