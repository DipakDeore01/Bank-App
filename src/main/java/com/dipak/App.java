package com.dipak;

import com.dipak.service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService(sc);

        while (true) {
            System.out.println("*-----------------------*\n" +
                    "|        BankPay        |\n" +
                    "*-----------------------*");
            System.out.println("1) Open New Account");
            System.out.println("2) Existing Users");
            System.out.println("0) Exit\n");

            System.out.print("Enter Your Choice(1,2 or 0): ");
            try {
                byte choice = sc.nextByte();
                switch (choice) {
                    case 1 -> userService.register();
                    case 2 -> userService.login();
                    case 0 -> {
                        System.out.print("Existing The software...");
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
}