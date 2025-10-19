package com.dipak.service;

import com.dipak.dao.AccountDAO;
import com.dipak.dao.UserDAO;
import com.dipak.entity.Account;
import com.dipak.entity.Users;
import org.hibernate.Session;

import java.util.Random;
import java.util.Scanner;

import static com.dipak.util.HibernateUtil.getFactory;

public class UserService {
    Scanner sc;
    Users users = new Users();
    Account account = new Account();
    UserDAO userDAO = new UserDAO();
    AccountDAO accountDAO = new AccountDAO();
    Session session = getFactory().openSession();

    public UserService(){
    }

    public UserService(Scanner sc){
        this.sc=sc;
    }

    public void register(){
        System.out.println("\n*-------- Registration Form --------*\n");
        sc.nextLine();
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        users.setName(name);
        account.setName(name);

        System.out.print("Email: ");
        String email = sc.next();
        users.setEmail(email);
        phone();
        password();
        aadhaar();
        pan();
        sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        users.setAddress(address);
        deposit();
        pin();
        generateAccountNumber();

        try{
            userDAO.create(users);
            accountDAO.create(account);

            System.out.println("Registration Successful...");
            System.out.println("Your account number is: "+account.getAcc_no());
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void phone(){
        while (true){
            System.out.print("Phone Number: +91");
            String phone = sc.next();
            if (phone.matches("\\d{10}")){
                users.setPhone(phone);
                return;
            }else{
                System.out.println("Enter valid phone number...");
            }
        }
    }

    private void password(){
        while (true){
            System.out.println("Create 8 digit password");
            System.out.print("Password: ");
            String pass = sc.next();
            if (pass.length()>=8){
                System.out.print("Re-Enter Password: ");
                String rpass = sc.next();
                if (pass.equals(rpass)){
                    users.setPassword(pass);
                    return;
                }else {
                    System.out.println("Enter same password while re-entering password...\n");
                }
            }else{
                System.out.println("Enter minimum 8 digit password...\n");
            }
        }
    }

    private void aadhaar() {
        while (true) {
            System.out.print("Aadhaar Number (12 digits): ");
            String aadhaar = sc.next();
            if (aadhaar.matches("\\d{12}")) {
                users.setAadhaar(aadhaar);
                return;
            } else {
                System.out.println("Invalid Aadhaar number. Please enter exactly 12 digits.\n");
            }
        }
    }

    private void pan() {
        while (true) {
            System.out.print("PAN Number: ");
            String pan = sc.next();
            pan = pan.toUpperCase();
            if (pan.matches("(?i)[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
                users.setPan(pan);
                return;
            } else {
                System.out.println("Invalid PAN number. Please enter again (e.g., ABCDE1234F).");
            }
        }
    }

    private void deposit() {
        System.out.println("\nPlease deposit at least Rs.1000 to activate your account.");

        while (true) {
            System.out.print("Enter deposit amount: ");
            double amount = sc.nextDouble();

            if (amount >= 1000) {
                account.setBalance(amount);
                return;
            } else {
                System.out.println("Minimum deposit is Rs.1000. Please try again.\n");
            }
        }
    }

    private void pin(){
        System.out.println("You want to set a 4 digit pin for transaction");
        while (true) {
            System.out.print("Pin: ");
            String pin = sc.next();
            if (pin.matches("\\d{4}")) {
                System.out.print("Re-Enter Pin: ");
                String rpin = sc.next();
                if (pin.equals(rpin)){
                    account.setPin(pin);
                    return;
                }else {
                    System.out.println("Enter same pin while re-entering pin...\n");
                }
            } else {
                System.out.println("Enter valid 4 digit numeric pin...");
            }
        }
    }

    private void generateAccountNumber() {
        Random random = new Random();
        String accNumber;

        while (true) {
            accNumber = "ACC" + (1000000000L + (long)(random.nextDouble() * 9000000000L));

            Account existingAccount = session.createQuery(
                            "from Account where acc_no = :accNumber", Account.class)
                    .setParameter("accNumber", accNumber)
                    .uniqueResult();

            if (existingAccount == null) {
                break;
            }
        }
        users.setAcc_no(accNumber);
        account.setAcc_no(accNumber);
    }

    public void login() {
        while (true) {
            System.out.print("Email: ");
            String email = sc.next();

            Users existingAccount = session.createQuery(
                            "from Users where email = :email", Users.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (existingAccount == null) {
                System.out.println("No account found with this email...\n");
                break;
            } else {
                System.out.print("Password: ");
                String password = sc.next();

                if (existingAccount.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + existingAccount.getName());
                    break;
                } else {
                    System.out.println("Incorrect password. Try again.");
                    System.out.println("Do you want to try again? (yes/no)");
                    String choice = sc.next();

                    if (choice.equalsIgnoreCase("no")) {
                        break;
                    }
                }
            }
        }
    }


}
