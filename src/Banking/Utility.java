package Banking;

import java.util.Random;
import java.util.Scanner;

public class Utility {
    private static final Scanner scanner = new Scanner(System.in); // Single instance

    public static String AccountNumberGenerator(int length) {
        if (length < 9) throw new IllegalArgumentException("Account number must be at least 9 digits");

        int[] account = new int[length - 1]; // Reserve 1 digit for Luhn check
        Random rand = new Random();
        int sum = 0;
        boolean isEven = false;
        StringBuilder accountNumber = new StringBuilder();

        for (int i = account.length - 1; i >= 0; i--) {
            account[i] = rand.nextInt(10);
            sum += isEven ? getLuhnDouble(account[i]) : account[i];
            isEven = !isEven;
        }

        for (int digit : account) {
            accountNumber.append(digit);
        }

        int lastDigit = (10 - (sum % 10)) % 10;
        accountNumber.append(lastDigit);

        return accountNumber.toString();
    }

    private static int getLuhnDouble(int number) {
        int doubled = number * 2;
        return (doubled > 9) ? (doubled - 9) : doubled;
    }

    public static String getValidString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static void printMenu(User user, Bank bank) {
        while (true) {
            String accountType = selectAccountType();
            if (accountType == null) return; // User chose to exit

            var account = accountType.equals("Checking") ? user.getCheckingAccount() : user.getSavingAccount();

            while (true) {
                System.out.println("\n=====" + user.getFirstName() + " " + user.getLastName() + " Welcome to bank system! =====");
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Check Balance");
                System.out.println("4. Exit");

                int choice = getValidInt("Enter your choice: ");

                switch (choice) {
                    case 1 -> {
                        int amount = getValidInt("Please enter the amount you would like to deposit: ");
                        bank.deposit(account, amount);
                    }
                    case 2 -> bank.withdraw(account);
                    case 3 -> bank.checkBalance(account);
                    case 4 -> { return; }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    private static String selectAccountType() {
        while (true) {
            System.out.println("Please choose your account type: ");
            System.out.println("1. Checking account");
            System.out.println("2. Saving account");
            System.out.println("3. Exit");

            int choice = getValidInt("Enter your choice: ");

            return switch (choice) {
                case 1 -> "Checking";
                case 2 -> "Saving";
                case 3 -> null;
                default -> {
                    System.out.println("Invalid choice, please try again.");
                    yield null;
                }
            };
        }
    }

    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } else {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}