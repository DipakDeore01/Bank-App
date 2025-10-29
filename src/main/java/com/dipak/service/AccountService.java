package com.dipak.service;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountService {
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
                    case 3 -> System.out.println();
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

}
