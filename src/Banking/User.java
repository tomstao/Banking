package Banking;

import static Banking.Utility.getValidString;

public class User {
    final private String firstName;
    final private String lastName;
    final private String accountNumber;
    private String passwordHash;
    private final Account account;

    public User() {
        this.firstName = getValidString("Enter your first name: ");
        this.lastName = getValidString("Enter your last name: ");
        this.passwordHash = hashPassword(getValidString("Enter your password: "));

        // 🔹 FIX: Get account type BEFORE calling createAccountForUser()
        String accountType;
        while (true) {
            accountType = getValidString("Enter the account type (Savings/Checking): ").trim();
            if (accountType.equalsIgnoreCase("Savings") || accountType.equalsIgnoreCase("Checking")) {
                break;
            }
            System.out.println("Invalid account type. Please enter 'Savings' or 'Checking'.");
        }

        // 🔹 FIX: Now pass the validated account type
        this.account = Account.createAccountForUser(this, accountType);
        this.accountNumber = account.getAccountNumber();  // Retrieve from Account
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
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
}