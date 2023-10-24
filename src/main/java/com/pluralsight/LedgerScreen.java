package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class LedgerScreen {
    public static String transactionFile = "src/main/resources/transactions.csv";
    private static DateTimeFormatter time =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static int count = 0;
    public static HashMap<String, Double>ledger;

    public static void ledgerScreen (Scanner scan, HashMap<String, Double> ledger){
        while (true){
            System.out.println("Ledger options: ");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("O) Back");
            System.out.println("H) Home");

            System.out.println("What would you like to do?: ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("A")) {

                displayLedger(ledger);

            } else if (choice.equalsIgnoreCase("D")) {

                displayFilteredLedger(ledger, true);

            } else if (choice.equalsIgnoreCase("P")) {

                displayFilteredLedger(ledger, false);

            } else if (choice.equalsIgnoreCase("R")) {

                reportScreen(scan, ledger);

            } else if (choice.equalsIgnoreCase("O")) {

                break;

            }else if (choice.equalsIgnoreCase("H")) {

                return;

            }else {
                System.out.println("ERROR! ERROR!\n" +
                        "invalid input\n" +
                        "Please try again: ");
            }
        }
    }

    private static void displayLedger(HashMap<String, Double> ledger) {
        if (ledger.isEmpty()) {

            System.out.println("No entries found.");

        }else {
            System.out.println("Entries");
            for (String key : ledger.keySet()) {
                System.out.println(key + "-" + ledger.get(key));

            }
        }
    }

    private static void reportScreen(Scanner scan,HashMap<String, Double> ledger) {
        while (true) {
            System.out.println("Report Screen");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3 Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.println("H) Home");

            System.out.print("What would you like to do?: ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {

                LocalDate sDate = LocalDate.now().withDayOfMonth(1);
                LocalDate eDate = LocalDate.now();
                displayReport (ledger, sDate, eDate);

            }else if (choice.equalsIgnoreCase("2")) {

                LocalDate sDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                LocalDate eDate = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
                displayReport (ledger,sDate, eDate);

            } else if (choice.equalsIgnoreCase("3")) {

                LocalDate sDate = LocalDate.now().withDayOfYear(1);
                LocalDate eDate = LocalDate.now();
                displayReport(ledger, sDate, eDate);

            } else if (choice.equalsIgnoreCase("4")) {

                LocalDate sDate =LocalDate.now().minusYears(1).withDayOfYear(1);
                LocalDate eDate =LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
                displayReport(ledger, sDate, eDate);

            } else if (choice.equalsIgnoreCase("5")) {
                //vendor searched
                System.out.println("Enter Vendor name: ");
                String vendor = scan.nextLine();
                displayFilteredLedgerByVendor(ledger, vendor);
            } else if (choice.equalsIgnoreCase("0")) {
                //goes back to home screen
                break;
            } else if (choice.equalsIgnoreCase("H")) {

                return;

            }else {
                System.out.println("ERROR! ERROR! Invalid input\n" +
                        "Please try again: ");
            }
        }
    }
    private static void displayLedger (Scanner scan, HashMap<String, Double> ledger) {

        if (ledger.isEmpty()) {

            System.out.println("No entries found.");

        }else {
            System.out.println("Entries");
            for (String key : ledger.keySet()) {
                System.out.println(key + "-" + ledger.get(key));

            }
        }
    }
    private static void displayFilteredLedger (HashMap<String, Double> ledger, boolean displayDeposits) {

        if (ledger.isEmpty()) {

            System.out.println("No entries found");

        }else {

            System.out.println("Filtered Entries");

            for (String key : ledger.keySet()) {

                double amount = ledger.get(key);
                if ((displayDeposits && amount > 0) || (!displayDeposits && amount < 0)) {
                    System.out.println(key + "|" + amount);

                }
            }
        }
    }
    private static void displayFilteredLedgerByVendor (HashMap<String, Double>ledger, String vendor) {

        if (ledger.isEmpty()) {
            System.out.println("No entries found");

        }else {

            System.out.println("Filtered Entries for Vendor " + vendor);

            for (String key : ledger.keySet()) {

                if (key.contains(vendor)) {
                    System.out.println(key + "|" + ledger.get(key));
                }
            }
        }
    }
    private static void displayReport (HashMap<String, Double>ledger, LocalDate sDate, LocalDate eDate) {
        if (ledger.isEmpty()) {
            System.out.println("No entries found in ledger");
        }else {
            System.out.println("Entry reports");
            for (String key : ledger.keySet()) {
                LocalDate date = LocalDate.parse(getDateFromKey(key), time);

                if (!date.isBefore(sDate) && !date.isAfter(eDate)) {
                    System.out.println(key + "|"  + ledger.get(key));
                }
            }
        }
    }
    public static void saveTransaction(String description, double amount){
        try {
            String input;
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);
            BufferedWriter name = new BufferedWriter(fileWriter);
            String formattedDate;
            String timeCSV;
            String vendor;
            
            while ((input = bufferedReader.readLine()) != null) {
                String[] transactionFile = input.split("\\|");
                if (!transactionFile[0].equals("date")) {
                    formattedDate = String.valueOf(LocalDate.parse(transactionFile[0]));
                    timeCSV = String.valueOf(LocalTime.parse(transactionFile[1]));
                    description = transactionFile[2];
                    vendor = transactionFile[3];
                    amount = Double.parseDouble(transactionFile[4]);
                    ledger.put(count, new LedgerScreen(formattedDate));
                    count++;
                    
                    String transactionEntries = formattedDate +"|" + description + "|" + amount;

                    name.write(transactionEntries);
                    name.newLine();
                    name.close();
                }
            }
            

        } catch (IOException error) {
            System.out.println("An error has occurred, file not saved");
            error.printStackTrace();
        }
    }
    private static String getDateFromKey(String userPrompt) {
        return userPrompt.split("|")[0];
    }

}
