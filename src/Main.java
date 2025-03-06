import Banking.Bank;
import Banking.User;
import Banking.Utility;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        while (true) {
            System.out.println("Welcome to the Bank");
            System.out.println("Please enter your choice");
            System.out.println("1. User register");
            System.out.println("2. Login");
            switch (scanner.nextLine()) {
                case "1":
                    User newUser = bank.registerUser();
                    System.out.println("New user registered: " + newUser.getFirstName() + " " + newUser.getLastName() +
                            ", to continue press 1, or press other key to exit:");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        Utility.printMenu(newUser, bank);
                    } else {
                        System.out.println("Thanks you for choosing our bank!");
                        continue;
                    }
                     break;
                case "2":
                   User loggedInUser = bank.authenticateUser(scanner.nextLine(), scanner.nextLine());
                   if (loggedInUser != null)
                {
                    Utility.printMenu(loggedInUser, bank);
                } else continue;
                default: System.out.println("Invalid choice");

            }

        }


    }


}