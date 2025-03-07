package Banking;

import java.util.Random;
import java.util.Scanner;

public class Utility {
    static public String AccountNumberGenerator(int length) {
        if (length < 9) throw new IllegalArgumentException("Account number must be at least 9 digits");

        int[] account = new int[length - 1]; // Reserve 1 digit for Luhn check
        boolean isEven = false;
        Random rand = new Random();
        int sum = 0;
        StringBuilder accountNumber = new StringBuilder();

        for (int i = account.length - 1; i >= 0; i--) {
            account[i] = rand.nextInt(10);
            if (isEven) {
                sum += getLuhnDouble(account[i]);
            } else {
                sum += account[i];
            }
            isEven = !isEven;
        }

        for (int digit : account) {
            accountNumber.append(digit);
        }

        int lastDigit = (10 - (sum % 10)) % 10;
        accountNumber.append(lastDigit);

        return accountNumber.toString();
    }
    static private int getLuhnDouble(int number) {
        int doubled = number * 2;
        return (doubled > 9) ? (doubled - 9) : doubled;
    }

    public static String getValidString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static void printMenu(User user, Bank bank) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder accountType = new StringBuilder("Checking");
        while (true) {

            System.out.print("Please choose your account type: ");
            System.out.println("1. Checking account");
            System.out.println("2. Saving account");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: accountType = new StringBuilder("Checking"); break;
                case 2: accountType = new StringBuilder("Saving"); break;
                case 3: return;
                default: System.out.println("Invalid choice");
        }

        var account = accountType.toString().equals("Checking") ? user.getCheckingAccount() : user.getSavingAccount();
        while (true)
        {
            System.out.println("\n=====" + user.getFirstName() + " " + user.getLastName() + " Welcome to bank system! =====");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            //System.out.println("4. Open Account");
            System.out.println("4. Exit");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Please enter the amount you would like to deposit: ");
                    bank.deposit(account,scanner.nextInt());
                }
                case 2 -> {
                    System.out.println("Please enter the amount you would like to withdraw: ");
                    bank.withdraw(account);
                }

                case 3 -> bank.checkBalance(account);

                case 4 -> {
                    return;
                    }

                }
            }
        }

    }

}
