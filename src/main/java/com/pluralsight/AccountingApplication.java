package com.pluralsight;
import java.util.HashMap;
import java.util.Scanner;

import static com.pluralsight.LedgerScreen.*;

public class AccountingApplication {

    public static void main(String[] args) {
        HashMap<String, Double> ledger = new HashMap<>();
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println("Home Screen");
            System.out.println("D) Add Deposit\n" +
                    "P) Make Payment(Debit)\n" +
                    "L) Ledger\n" +
                    "X) Exit");

            System.out.println("What would you like to do?");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("D")) {
                System.out.print("What is the deposit amount?: ");
                double amount = scan.nextDouble();
                scan.nextLine();

                saveTransaction("Deposit", amount);

                System.out.println("Deposit Successful!");
            } else if (choice.equalsIgnoreCase("P")) {

                System.out.print("What is the debit amount?: ");
                double amount = scan.nextDouble();

                scan.nextLine();

                saveTransaction("Payment", -amount);

                System.out.print("Payment Successful!");
            } else if (choice.equalsIgnoreCase("L")) {

                ledgerScreen(scan, ledger);

            }else if (choice.equalsIgnoreCase("X")) {
                break;
            }else {
                System.out.print("ERROR! ERROR!\n" +
                        "Please try again: ");
            }
        }
    }


}
