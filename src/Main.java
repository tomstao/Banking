import Banking.Bank;
import Banking.User;
import Banking.Utility;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // Use a single scanner instance

    public static void main(String[] args) {
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n=== Welcome to the Bank ===");
            System.out.println("1. User Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> registerUser(bank);
                case "2" -> loginUser(bank);
                case "3" -> {
                    System.out.println("Thank you for choosing our bank! Goodbye.");
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void registerUser(Bank bank) {
        User newUser = bank.registerUser();
        System.out.println("New user registered: " + newUser.getFirstName() + " " + newUser.getLastName());
        System.out.print("Press 1 to continue to menu, or any other key to exit: ");

        if (scanner.hasNextInt() && scanner.nextInt() == 1) {
            scanner.nextLine(); // Consume newline
            Utility.printMenu(newUser, bank);
        } else {
            System.out.println("Thank you for choosing our bank!");
            scanner.nextLine(); // Consume invalid input
        }
    }

    private static void loginUser(Bank bank) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = bank.authenticateUser(username, password);
        if (loggedInUser != null) {
            Utility.printMenu(loggedInUser, bank);
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
}