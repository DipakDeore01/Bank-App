package com.dipak;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("*-----------------------*\n" +
                    "|        BankPay        |\n" +
                    "*-----------------------*");
            System.out.println("1) Open New Account");
            System.out.println("2) Existing User");
            System.out.println("0) Exit\n");

            System.out.print("Enter Your Choice(1,2 or 0): ");
            try {
                byte choice = sc.nextByte();
                switch (choice) {
                    case 1 -> System.out.println("Register...");
                    case 2 -> System.out.println("Existing User...");
                    case 0 -> {
                        System.out.println("Existing The software...");
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