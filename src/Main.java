import Banking.Bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = Bank.getInstance();
        while (true) {
            System.out.println("Welcome to the Bank");
            System.out.println("Please enter your choice");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            switch (scanner.nextLine()) {
                case "1": System.out.println("Your account created"); break;
                case "2": System.out.println("Your account logged in"); break;
                default: System.out.println("Invalid choice");
            }

        }


    }


}