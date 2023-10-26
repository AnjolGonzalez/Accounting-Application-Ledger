package com.pluralsight;
import java.io.IOException;
import java.util.Scanner;
import static com.pluralsight.LedgerScreen.*;

public class AccountingApplication {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        while(true) {
            //HomeScreen - main
            System.out.println("\nHome Screen");
            System.out.println("D) Add Deposit\n" +
                    "P) Make Payment(Debit)\n" +
                    "L) Ledger\n" +
                    "X) Exit");

            System.out.print("What would you like to do? ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("D")) {
                System.out.print("What is the deposit amount?: ");
                double amount = scan.nextDouble();
                scan.nextLine();

                System.out.println("What is the Vendor name?: ");
                String vendor = scan.nextLine();

                saveTransaction("Deposit", amount, vendor);

                System.out.println("Deposit Successful!");

            } else if (choice.equalsIgnoreCase("P")) {

                System.out.print("What is the debit amount?: ");
                double amount = scan.nextDouble();
                scan.nextLine();
                amount *= -1;
                System.out.println("What is the Vendor name?: ");
                String vendor = scan.nextLine();

                saveTransaction("Payment", amount, vendor);


                System.out.print("Payment Successful!\n" +
                        " ");

            } else if (choice.equalsIgnoreCase("L")) {

                ledgerScreen(scan);

            }else if (choice.equalsIgnoreCase("X")) {

                break;

            }else {
                System.out.print("ERROR! ERROR!\n" +
                        "Please try again: ");

            }
        }
    }


}
