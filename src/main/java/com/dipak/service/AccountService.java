package com.dipak.service;

import com.dipak.dao.AccountDAO;
import com.dipak.entity.Account;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountService {
    Account account = new Account();
    AccountDAO accountDAO = new AccountDAO();
    Scanner sc;
    private static File file = new File("src/main/java/com/dipak/login.txt");

    public AccountService(){
    }

    public AccountService(Scanner sc){
        this.sc=sc;
    }

    public void menu(){
        while(true){

            System.out.println("*----- Money Transfer -----*");
            System.out.println("1) To Mobile Number\n" +
                    "2) To Bank Account\n" +
                    "3) Check Balance\n" +
                    "0) Logout\n");

            System.out.print("Enter Your Choice(1,2,3 or 0): ");
            try {
                byte choice = sc.nextByte();
                switch (choice) {
                    case 1 -> System.out.println();
                    case 2 -> System.out.println();
                    case 3 -> viewBalance();
                    case 0 -> {
                        System.out.println("Logging Out...");
                        logout();
                        return;
                    }
                    default -> System.out.println("Enter valid input...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter valid input...");
                sc.nextLine();
            }
        }
    }

    private void logout() {
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Logout Successful...\n");
            } else {
                System.out.println("Error occurred while logging out. Could not delete file.\n");
            }
        } else {
            System.out.println("You are already logged out or session file not found.\n");
        }
    }

    private void viewBalance() {
        String acc_no = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                if (data.length > 2) {
                    acc_no = data[2];
                } else {
                    System.out.println("Invalid file format: missing account number.");
                    return;
                }
            } else {
                System.out.println("File is empty.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (acc_no != null) {
            Account account = accountDAO.view(acc_no);
            if (account != null) {
                System.out.println("Account Balance: " + account.getBalance());
                System.out.println();
            } else {
                System.out.println("Account not found for account number: " + acc_no);
                System.out.println();
            }
        }
    }


}
