package Banking;

import static Banking.Utility.getValidString;

public class User {
    final private String firstName;
    final private String lastName;
    final private String savingAccountNumber;
    final private String checkingAccountNumber;
    private String passwordHash;
    private final SavingsAccount savingAccount;
    private final CheckingAccount checkingAccount;

    public User() {
        this.firstName = getValidString("Enter your first name: ");
        this.lastName = getValidString("Enter your last name: ");
        this.passwordHash = hashPassword(getValidString("Enter your password: "));

        // 🔹 FIX: Get account type BEFORE calling createAccountForUser()
//        String accountType;
//        while (true) {
//            accountType = getValidString("Enter the account type (Savings/Checking): ").trim();
//            if (accountType.equalsIgnoreCase("Savings") || accountType.equalsIgnoreCase("Checking")) {
//                break;
//            }
//            System.out.println("Invalid account type. Please enter 'Savings' or 'Checking'.");
//        }

        // 🔹 FIX: Now pass the validated account type
//        this.account = Account.createAccountForUser(this, accountType);
//        this.accountNumber = account.getAccountNumber();  // Retrieve from Account
        this.savingAccount = new SavingsAccount();
        this.checkingAccount = new CheckingAccount();
        this.savingAccountNumber = this.savingAccount.getAccountNumber();
        this.checkingAccountNumber = this.checkingAccount.getAccountNumber();

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public boolean verifyPassword(String inputPassword) {
        return hashPassword(inputPassword).equals(passwordHash);
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password");
        }
    }

    public void passwordReset() {
        String oldPassword = getValidString("Enter your old password: ");
        if (verifyPassword(oldPassword)) {
            String newPassword;

            // Ensure new password is not empty and different from the old password
            while (true) {
                newPassword = getValidString("Enter your new password: ").trim();
                if (!newPassword.isEmpty() && !verifyPassword(newPassword)) {
                    break;
                }
                System.out.println("New password must be different from the old password and cannot be empty.");
            }

            // 🔹 FIX: Store the new hashed password
            this.passwordHash = hashPassword(newPassword);
            System.out.println("Password has been successfully reset.");
        } else {
            System.out.println("Invalid password. Please try again.");
        }
    }

    public String getSavingAccountNumber() {
        return savingAccountNumber;
    }

    public String getCheckingAccountNumber() {
        return checkingAccountNumber;
    }

    public SavingsAccount getSavingAccount() {
        return savingAccount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }
}