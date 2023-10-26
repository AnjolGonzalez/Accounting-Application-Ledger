package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LedgerScreen {
    public static String transactionFile = "src/main/resources/transactions.csv";

    public static ArrayList<Ledger> ledgerArrayList = new ArrayList<>();

    public static void ledgerScreen (Scanner scan) throws IOException {
        //This is to make sure if they put the wrong thing my code won't break
        readFile();
        while (true){
            System.out.println("\nLedger Screen:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("O) Back");
            System.out.println("H) Home");

            System.out.print("What would you like to do?: ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("A")) {

                displayLedger(ledgerEntryList(ledgerArrayList));

            } else if (choice.equalsIgnoreCase("D")) {

                displayFilteredLedger(true, ledgerEntryList(ledgerArrayList));

            } else if (choice.equalsIgnoreCase("P")) {

                displayFilteredLedger(false, ledgerEntryList(ledgerArrayList));

            } else if (choice.equalsIgnoreCase("R")) {

                reportScreen(scan, ledgerEntryList(ledgerArrayList));

            } else if (choice.equalsIgnoreCase("O")) {

                break;

            }else if (choice.equalsIgnoreCase("H")) {

                return;

            }else {
                System.out.println("ERROR! ERROR!\n" +
                        "invalid input\n" +
                        "Please try again:\n ");
            }
        }
    }

    public static ArrayList<Ledger> ledgerEntryList(ArrayList<Ledger>ledgerArrayList) {
        ledgerArrayList.sort((key1, key2) -> {
            //key 1 represents the date and key 2 represents the time

            LocalDateTime day1 = LocalDateTime.of(key1.getDate(), key1.getTime());
            LocalDateTime day2 = LocalDateTime.of(key2.getDate(), key2.getTime());
            return day2.compareTo(day1);

        });
        return ledgerArrayList;
    }

    private static void displayLedger(ArrayList<Ledger> ledgerArrayList) {
        if (LedgerScreen.ledgerArrayList.isEmpty()) {

            System.out.println("No entries found.");

        }else {
            System.out.println("\nEntries");
            for (Ledger key : LedgerScreen.ledgerArrayList) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                        key.getDate(), key.getTime(), key.getDescription(), key.getVendor(), key.getAmount());

            }
        }
    }

    private static void reportScreen(Scanner scan, ArrayList<Ledger> ledgerArrayList) {
        while (true) {
            System.out.println("\nReport Screen: ");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.println("H) Home");

            System.out.print("What would you like to do?: ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                /*All these methods are doing is setting up the calculations on reading the data in file and outputting
                the correct way*/

                LocalDate sDate = LocalDate.now().minusMonths(1);
                LocalDate eDate = LocalDate.now().plusMonths(1);
                displayReport (LedgerScreen.ledgerArrayList, sDate, eDate);

            }else if (choice.equalsIgnoreCase("2")) {

                LocalDate sDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                LocalDate eDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
                displayReport (LedgerScreen.ledgerArrayList,sDate, eDate);

            } else if (choice.equalsIgnoreCase("3")) {

                LocalDate sDate = LocalDate.now().withDayOfYear(1);
                LocalDate eDate = LocalDate.now();
                displayReport(LedgerScreen.ledgerArrayList, sDate, eDate);

            } else if (choice.equalsIgnoreCase("4")) {

                LocalDate sDate =LocalDate.now().minusYears(1).withDayOfYear(1);
                LocalDate eDate =LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
                displayReport(LedgerScreen.ledgerArrayList, sDate, eDate);

            } else if (choice.equalsIgnoreCase("5")) {

                System.out.println("Enter Vendor name: ");
                String vendor = scan.nextLine();
                displayFilteredLedgerByVendor(LedgerScreen.ledgerArrayList, vendor);

            } else if (choice.equalsIgnoreCase("0")) {
                //goes back one
                break;

            } else if (choice.equalsIgnoreCase("H")) {
                //goes to go back home
                return;

            }else {
                System.out.println("ERROR! ERROR! Invalid input\n" +
                        "Please try again: ");
            }
        }
    }
    //report
    private static void displayReport (ArrayList<Ledger> ledger, LocalDate sDate, LocalDate eDate) {
        //This displays report screen read from the array

        if (ledger.isEmpty()) {

            System.out.println("No entries found in ledger");

        }else {

            System.out.println("\nEntry reports: ");

            for (Ledger key : ledgerArrayList) {

                LocalDate date = key.getDate();

                //&& evaluates expression to return boolean
                if (date.isBefore(eDate) && date.isAfter(sDate)) {
                    //This checks the date if it's before the specified date
                    //The second one checks the date if it's after the specified one
                    System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                            key.getDate(), key.getTime(), key.getDescription(), key.getVendor(), key.getAmount());                }
            }
        }
    }

    private static void displayFilteredLedger (boolean displayDeposits, ArrayList<Ledger> ledgerArrayList) {
        //displays filtered ledger if not empty

        if (LedgerScreen.ledgerArrayList.isEmpty()) {

            System.out.println("No entries found");

        }else {

            System.out.println("\nEntries: ");

            for (Ledger key : LedgerScreen.ledgerArrayList) {

                double amount = key.getAmount();
                if ((displayDeposits && amount > 0) || (!displayDeposits && amount < 0)) {
                    System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                            key.getDate(), key.getTime(), key.getDescription(), key.getVendor(), key.getAmount());
                }
            }
        }
    }
    private static void displayFilteredLedgerByVendor (ArrayList<Ledger> ledger, String vendor) {

        if (ledger.isEmpty()) {
            System.out.println("No entries found");

        }else {

            System.out.println("\nEntries for Vendor " + vendor);

            for (Ledger key : ledgerArrayList) {

                if (key.getVendor().equalsIgnoreCase(vendor)) {
                    System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                            key.getDate(), key.getTime(), key.getDescription(), key.getVendor(), key.getAmount());
                }
            }
        }
    }

    public static void saveTransaction(String description, double amount, String vendor){
        try {
            LocalDate date = LocalDate.now();
            LocalTime timeCSV = LocalTime.now();
            DateTimeFormatter dTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            timeCSV = LocalTime.parse(dTime.format(timeCSV));

            FileWriter fileWriter = new FileWriter(transactionFile, true);
            BufferedWriter name = new BufferedWriter(fileWriter);

            ledgerArrayList.add(new Ledger(date, timeCSV, description, vendor, amount));

                    String transactionEntries = date +"|" + timeCSV + "|" + description + "|" + vendor + "|" + amount;

                    name.write(transactionEntries);
                    name.newLine();
                    name.close();


        } catch (IOException error) {
            System.out.println("An error has occurred, file not saved");
            error.printStackTrace();
        }
    }
    public static void readFile() throws IOException {
        String input;
        LocalDate date = LocalDate.now();//currently immutable
        LocalTime timeCSV = LocalTime.now();
        String description;
        String vendor;
        double amount;

        DateTimeFormatter dTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeCSV = LocalTime.parse(dTime.format(timeCSV));
        FileReader fileReader = new FileReader(transactionFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((input = bufferedReader.readLine())!= null) {

            String[] transactionLists = input.split("\\|");

            if (!transactionLists[0].equals("date")) {

                date = LocalDate.parse(transactionLists[0]);
                timeCSV = LocalTime.parse(transactionLists[1]);
                description = transactionLists[2];
                vendor = transactionLists[3];
                amount = Double.parseDouble(transactionLists[4]);

                ledgerArrayList.add(new Ledger(date, timeCSV, description, vendor, amount));

            }

        }
    }
}
