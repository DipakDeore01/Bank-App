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
            System.out.println("*----- Money Transfer -----*");
            System.out.println("1) To Mobile Number\n2) To Bank Account\n3) Check Balance\n0) Logout\n");
            System.out.print("Enter Your Choice(1,2,3 or 0): ");
            try {
                byte choice = sc.nextByte();
                switch (choice) {
                    case 1 -> phone_trans();
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
            if (file.delete()) System.out.println("Logout Successful...\n");
            else System.out.println("Error occurred while logging out. Could not delete file.\n");
        } else System.out.println("You are already logged out or session file not found.\n");
    }

    private void viewBalance() {
        getAcc_no();
        if (acc_no != null) {
            Account account = accountDAO.view(acc_no);
            if (account != null) System.out.println("Account Balance: " + account.getBalance() + "\n");
            else System.out.println("Account not found for account number: " + acc_no + "\n");
        }
    }

    private String getAcc_no() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                if (data.length > 2) acc_no = data[2];
                else System.out.println("Invalid file format: missing account number.");
            } else System.out.println("File is empty.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return acc_no;
    }

    private void phone_trans() {
        getAcc_no();
        try (Session session = getFactory().openSession()) {
            Account sender = session.get(Account.class, acc_no);
            if (sender == null) {
                System.out.println("Your account not found.\n");
                return;
            }
            System.out.print("Enter Mobile Number: ");
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
            if (amount > sender.getBalance()) {
                System.out.println("Insufficient Balance...");
                return;
            }
            accountDAO.transfer(sender.getPhone(), receiver.getPhone(), amount);
            System.out.println("₹" + amount + " transferred successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
