package com.dipak.service;

import com.dipak.dao.AccountDAO;
import com.dipak.entity.Account;
import org.hibernate.Session;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.dipak.util.HibernateUtil.getFactory;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();
    Scanner sc;
    private String acc_no;
    private static File file = new File("src/main/java/com/dipak/login.txt");

    public AccountService() {}
    public AccountService(Scanner sc) { this.sc = sc; }

    public void menu() {
        while (true) {
            System.out.println("\n*----- Money Transfer -----*");
            System.out.println("1) Transfer To Mobile Number");
            System.out.println("2) Transfer To Bank Account");
            System.out.println("3) Check Balance");
            System.out.println("0) Logout");
            System.out.print("Enter Your Choice: ");

            try {
                byte choice = sc.nextByte();
                switch (choice) {
                    case 1 -> phone_trans();
                    case 2 -> acc_trans();
                    case 3 -> viewBalance();
                    case 0 -> {
                        logout();
                        return;
                    }
                    default -> System.out.println("Enter valid input...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter only numbers.");
                sc.nextLine();
            }
        }
    }

    private void logout() {
        if (file.exists() && file.delete()) {
            System.out.println("Logout Successful...\n");
        } else {
            System.out.println("You are already logged out or session file not found.\n");
        }
    }

    private void viewBalance() {
        getAcc_no();
        if (acc_no != null) {
            Account account = accountDAO.view(acc_no);
            if (account != null) {
                System.out.println("Account Balance: ₹" + account.getBalance() + "\n");
            } else {
                System.out.println("Account not found for account number: " + acc_no + "\n");
            }
        }
    }

    private String getAcc_no() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                if (data.length > 2) {
                    acc_no = data[2];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return acc_no;
    }

    private void phone_trans() {
        getAcc_no();
        if (acc_no == null) {
            System.out.println("Account not logged in!\n");
            return;
        }
        try (Session session = getFactory().openSession()) {
            Account sender = session.get(Account.class, acc_no);
            if (sender == null) {
                System.out.println("Your account not found.\n");
                return;
            }

            System.out.print("Enter Receiver Mobile Number: ");
            String phone = sc.next();

            Account receiver = session.createQuery("from Account where phone = :phone", Account.class)
                    .setParameter("phone", phone)
                    .uniqueResult();

            if (receiver == null) {
                System.out.println("No account found with this mobile number...\n");
                return;
            }

            System.out.print("Amount to Transfer: ");
            double amount = sc.nextDouble();

            accountDAO.transfer(sender.getPhone(), receiver.getPhone(), amount);
        }
    }

    private void acc_trans() {
        getAcc_no();
        if (acc_no == null) {
            System.out.println("Account not logged in!\n");
            return;
        }
        try (Session session = getFactory().openSession()) {
            Account sender = session.get(Account.class, acc_no);
            if (sender == null) {
                System.out.println("Your account not found.\n");
                return;
            }

            System.out.print("Enter Receiver Account Number: ");
            String receiverAccNo = sc.next();

            System.out.print("Amount to Transfer: ");
            double amount = sc.nextDouble();

            accountDAO.accountTransfer(acc_no, receiverAccNo, amount);
        }
    }
}
